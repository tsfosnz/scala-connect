package service.post

import java.text.SimpleDateFormat

import core.Service
import models.{PostEntity, CategoryItem, Post}
import service.category.{CategoryServ, CategoryItemServ}
import slick.driver.MySQLDriver.api._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object PostServ extends Service[Post](
  "mydb",
  (tag: Tag) => new Post(tag)) {


  def test = {


  }

  /**
   * Get a list of data with post, category by (page, count)
   *
   * @param page
   * @param count
   * @return
   */
  def getAllBy(page: Int, count: Int) = {

    try {

      val list = for {

        l <- query
        i <- CategoryItemServ.query
        c <- CategoryServ.query
        if (l.id === i.itemId && i.itemType === "post" && c.id === i.categoryId)

      } yield (l, c.name)

      val q = list.drop(page).take(count)
      val sql = q.result.statements.head

      println(sql)

      val action = q.result
      val result = db.run(action)

      result
    }

    catch {
      // catch all error, will this isn't recommanded
      // just a simple try
      case err: Throwable => null
    }

  }

  /**
   * Get a list of data (post, category) by (category id, page, count)
   *
   * @param categoryId
   * @param page
   * @param count
   * @return
   */
  def getAllBy(categoryId: Int, page: Int, count: Int) = {

    try {

      val list = for {

        p <- query
        ci <- CategoryItemServ.query
        c <- CategoryServ.query
        if (p.id === ci.itemId
          && ci.itemType === "post"
          && c.id === ci.categoryId)

      } yield (p, c.name, c.id)

      val q = list.filter(_._3 === categoryId).drop(page).take(count)
      val sql = q.result.statements.head

      println(sql)

      val action = q.result
      val result = db.run(action)

      result
    }

    catch {
      case err: Throwable => null
    }

  }

  /**
   * Get a group of data (category, seq[post]) in fix size
   * by (page, count), will get them this way, first get
   * a list of category, then get a list of post for each
   * all in fixed size, the equal SQL is union all
   *
   * @param page
   * @param count
   * @return
   */
  def getGroupBy(categoryCount: Int, page: Int, count: Int) = {

    // so what's the more elegant way?

    try {

      val category = CategoryServ.query
      val categoryItem = CategoryItemServ.query

      //
      val categoryQuery = (for {

        c <- category

      } yield (c.id)).take(categoryCount)

      println(categoryQuery.result.statements.head)

      val postQuery = for {

        p <- query
        ci <- categoryItem if p.id === ci.itemId && ci.itemType === "post"
        c <- category if ci.categoryId === c.id

      } yield (p, c.name, c.id)


      val st = new scala.collection.mutable.Stack[Query[(Post, Rep[String], Rep[Int]), (PostEntity, String, Int), scala.Seq]]

      val result = for {

        item <- db.run(categoryQuery.result)

      } yield {

          // put each list to a collection
          // then use fold


          item.map { id =>

            item.size - item.indexOf(id) == 1 match {

              case true => st.push(postQuery.filter(_._3 === id).take(5 * item.size))
              case _ => st.push(postQuery.filter(_._3 === id).take(5))

            }

            println(item.indexOf(id))
          }


          val k = st.pop
          val unionQuery = st.foldRight(k)((a, b) => a ++ b)

          val q = unionQuery.drop(page).take(count).result

          //println(unionQuery.drop(page).take(count).result)
          println(q.statements.head)

          db.run(q)

        }

      result

    }

    catch {
      case err: Exception => null
    }

  }


  def one(id: Int) = {

    // first lets define a SQL

    try {

      val q = query.filter(_.id === id)
      val sql = query.result.statements.head

      println(sql)

      val action = q.result
      val result = db.run(action)

      result
    }

    catch {
      case err: Exception => null
    }

  }

  /**
   * Add the new item
   *
   * @param managerId the manager id
   * @param data the data of the new item
   * @return
   */
  def add(managerId: Int, data: Map[String, Seq[String]]) = {

    try {

      val dt: java.util.Date = new java.util.Date()
      val sdf: SimpleDateFormat =
        new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

      val now: String = sdf.format(dt)

      val action = DBIO.seq {

        query.map {

          model => (
            model.authorId,
            model.title,
            model.textBody,
            model.createdAt,
            model.updatedAt

            )
        } +=(
          managerId,
          data("title").head,
          data("body").head,
          now,
          now
          )
      }


      val sql = query.insertStatement

      // here we can see the pure sql from q
      println(sql)

      val result = db.run(action)

      result
    }

    catch {
      case err: Throwable => null
    }

  }

  def update(id: Int, data: Map[String, Seq[String]]) = {


    try {

      val dt: java.util.Date = new java.util.Date()
      val sdf: SimpleDateFormat =
        new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

      val now: String = sdf.format(dt)

      // should only add the value in data
      // and leave all esle alone

      val q = for {
        item <- query if item.id === id
      } yield (item.title, item.textBody, item.updatedAt)

      val action = q.update(data("title").head, data("body").head, now)
      val sql = q.updateStatement

      println(sql)

      val result = db.run(action)

      result
    }

    catch {
      case err: Throwable => null
    }


  }

  def remove(id: Int) = {

    // first lets define a SQL

    try {
      val q = query.filter(_.id === id)

      val action = q.delete
      val result = db.run(action)

      val sql = action.statements.head

      println(sql)

      result
    }


    catch {
      case err: Throwable => null
    }

  }


}
