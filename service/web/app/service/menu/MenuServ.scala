package service.menu

import core.Service
import models.{Menu, Topic}
import slick.driver.MySQLDriver.api._

object MenuServ extends Service[Menu](
  "mydb",
  (tag: Tag) => new Menu(tag)) {

  def test = {

    //println(t)


  }

  def getAllBy(page: Int, count: Int) = {

    try {

      //
      val q = (for {

        c <- query

      } yield c).drop(page).take(count)


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


  /*
    def one(id: Int) = {

      // first lets define a SQL

      try {

        val q = query.filter(_.id === id)
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
  */
}
