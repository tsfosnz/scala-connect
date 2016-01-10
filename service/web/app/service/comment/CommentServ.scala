package service.comment

import java.text.SimpleDateFormat

import core.Service
import models.{Post, Comment}
import slick.driver.MySQLDriver.api._

object CommentServ  extends Service[Comment](
  "mydb",
  (tag: Tag) => new Comment(tag)) {


  def test = {



  }

  def all(page: Int, count: Int) = {


    try {

      val q = query.drop(page).take(count)
      val sql = query.result.statements.head

      println(sql)

      val action = query.result
      val result = db.run(action)

      result
    }

    catch {
      case err: Throwable => null
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
      case err: Throwable => null
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
            model.textBody,
            model.createdAt,
            model.updatedAt

            )
        } +=(
          managerId,
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
      } yield (item.textBody, item.updatedAt)

      val action = q.update(data("text").head, now)
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
