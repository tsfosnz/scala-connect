package service.label

import java.text.SimpleDateFormat

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException
import models.{LabelPost, Label}
import slick.driver.MySQLDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object LabelPostIo {

  lazy val _query: TableQuery[LabelPost] = TableQuery[LabelPost]
  lazy val _db: Database = Database.forConfig("mydb")

  def test = {

    //println(t)


  }

  /*

    def all(page: Int, count: Int) = {


      try {

        val query = _query.drop(page).take(count)
        val sql = query.result.statements.head

        println(sql)

        val action = query.result
        val result = _db.run(action)


        //throw new Exception("Wrong!!")

        //_db.close

        result
      }

      catch {
        case err: Exception => null
      }

    }

    def one(id: Int) = {

      // first lets define a SQL

      try {

        val query = _query.filter(_.id === id)
        val sql = query.result.statements.head

        println(sql)

        val action = query.result
        val result = _db.run(action)

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

          _query.map {

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


        val sql = _query.insertStatement

        // here we can see the pure sql from query
        println(sql)

        val result = _db.run(action)

        result
      }

      catch {
        case err: Exception => null
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

        val query = for {
          item <- _query if item.id === id
        } yield (item.title, item.textBody, item.updatedAt)

        val action = query.update(data("title").head, data("body").head, now)
        val sql = query.updateStatement

        println(sql)

        val result = _db.run(action)

        result
      }

      catch {
        case err: Exception => null
      }


    }

    def remove(id: Int) = {

      // first lets define a SQL

      try {
        val query = _query.filter(_.id === id)

        val action = query.delete
        val result = _db.run(action)

        val sql = action.statements.head

        println(sql)

        result
      }

      catch {
        case err: Exception => null
      }

    }
  */

  /**
   * initialize the table, create its schema, update its schema
   */
  def initialize(drop: Boolean = false) = {


    val dropData= List(_query.schema.drop, _query.schema.create)
    val createData= List(_query.schema.create)


    val setup = if (drop)
      DBIO.seq(dropData: _*)
    else
      DBIO.seq(createData: _*)

    val setupFuture = _db.run(setup)

    setupFuture

    //_db.close()


  }

  /**
   * populate the data into the table
   */
  def populate(labelId: Int, postId: Int) = {

    val dt: java.util.Date = new java.util.Date()

    val sdf: SimpleDateFormat =
      new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    val now: String = sdf.format(dt)


    val setup = DBIO.seq(

      // _query,

      _query.map {

        m => (
          m.labelId,
          m.postId
          )
      } +=(
        labelId,
        postId
        )

    )

    val setupFuture = _db.run(setup)

    setupFuture


  }
}
