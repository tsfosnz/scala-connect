package service.post

import java.text.SimpleDateFormat

import core.Service
import models.{CategoryEntity, PostEntity, Post}
import service.category.{CategoryServ, CategoryItemServ}
import slick.driver.MySQLDriver.api._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * PostServ
 * 
 * @author Tom
 */

object PostServ extends Service[Post](
  "mydb",
  (tag: Tag) => new Post(tag)) {


  /**
   * Get a list of data (post, category) by (a list of category),
   * usage, when we display a list of category and for each of 
   * them, we will show a group of post in fix size
   *
   * @param categoryResult
   * @param postCount
   * @param page
   * @param count
   * @return
   */
  def getAllBy(categoryResult: Future[scala.Seq[CategoryEntity]],
               postCount: Int, 
               page: Int, 
               count: Int) = {

    // To explain: 
    //
    // SELECT * FROM category LIMIT 10
    // SELECT * FROM post INNER JOIN category_item ON .. WHERE category_id = 1 LIMIT 5 
    // UNION ALL
    // SELECT * FROM post INNER JOIN category_item ON .. WHERE category_id = 2 LIMIT 5
    // ...
    // SELECT * FROM post INNER JOIN category_item ON .. WHERE category_id = 10 LIMIT 5

    try {

      val category = CategoryServ.query
      val categoryItem = CategoryItemServ.query

      val postQuery = for {

        p <- query
        ci <- categoryItem if p.id === ci.itemId && ci.itemType === "post"
        c <- category if ci.categoryId === c.id

      } yield (p, c.name, c.id)


      val st = new scala.collection.mutable.Stack[Query[(Post, Rep[String], Rep[Int]), (PostEntity, String, Int), scala.Seq]]

      val result = for {

        item <- categoryResult

      } yield {

          item.map { c =>

            item.size - item.indexOf(c) == 1 match {

              case true => st.push(postQuery.filter(_._3 === c.id).take(postCount * 2))
              case _ => st.push(postQuery.filter(_._3 === c.id).take(postCount))

            }

            println(item.indexOf(c))
          }


          val k = st.pop
          val unionQuery = st.foldRight(k)((a, b) => a ++ b)

          val q = unionQuery.sortBy(_._3.asc).result

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

  /**
   * Get a list of data (post, category) by (category id) 
   *
   * @param categoryId
   * @param page
   * @param count
   * @return
   */
  def getAllBy(categoryId: Int, page: Int, count: Int) = {

    try {

      val category = CategoryServ.query
      val categoryItem = CategoryItemServ.query

      val postQuery = for {

        p <- query
        ci <- categoryItem if p.id === ci.itemId && ci.itemType === "post"
        c <- category if ci.categoryId === c.id

      } yield (p, c.name, c.id)

      val q = postQuery.filter(_._3 === categoryId).drop(page).take(count)

      println(q.result.statements.head)

      db.run(q.result)

    }

    catch {
      case err: Exception => null
    }

  }


  /**
   * Get one post by its id
   * 
   * @param id
   * @return
   */
  def one(id: Int) = {

    try {

      val q = query.filter(_.id === id)

      println(query.result.statements.head)

      db.run(q.result)

    }

    catch {
      case err: Exception => null
    }

  }

  /**
   * Add the new post
   *
   * @param authorId the manager id
   * @param data the data of the new item
   * @return
   */
  def add(authorId: Int, data: Map[String, Seq[String]]) = {

    try {

      // people said, use this only if it's java 8

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
          authorId,
          data("title").head,
          data("body").head,
          now,
          now
          )
      }

      println(query.insertStatement)

      db.run(action)
    }

    catch {
      case err: Exception => null
    }

  }

  /**
   * Update the post by id
   *
   * @param id
   * @param data
   * @return
   */
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

      println(q.updateStatement)

      db.run(action)
    }

    catch {
      case err: Exception => null
    }


  }

  /**
   * Remove the post by id
   *
   * @param id
   * @return
   */
  def remove(id: Int) = {

    // first lets define a SQL

    try {

      val q = query.filter(_.id === id)
      println(q.delete.statements.head)

      db.run(q.delete)
    }


    catch {
      case err: Exception => null
    }

  }


}
