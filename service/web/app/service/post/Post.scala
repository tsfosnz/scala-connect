package service.post

import java.text.SimpleDateFormat

import models.{Post, PostEntity}
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

object Post {

  lazy val _query: TableQuery[Post] = TableQuery[Post]
  lazy val _db: Database = Database.forConfig("mydb")

  def test = {

    _query.filter(_.authorId === 1)
    _query.map(

      model => {
        println((
          model.authorId,
          model.title
          ).getClass.getSimpleName)
      }
    )

    //println(t)


  }

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


  /**
   * initialize the table, create its schema, update its schema
   */
  def initialize() {

    val setup = DBIO.seq(

      //_query.schema.drop,
      //_query.schema.create


      //_query += Project(101, "Acme, Inc."),
      //_query += Project(49, "Superior Coffee"),
      //_query += Project(150, "The High Ground")

    )

    val setupFuture = _db.run(setup)

    setupFuture.onSuccess {
      case s => println("success: ", s)
    }

    setupFuture.onFailure {
      case s => println("failed", s)
    }

    //_db.close()


  }

  /**
   * populate the data into the table
   */
  def populate() {

    val dt: java.util.Date = new java.util.Date()

    val sdf: SimpleDateFormat =
      new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    val now: String = sdf.format(dt)


    val setup = DBIO.seq(

      // _query,

      _query += PostEntity(
        authorId = 1,
        title = faker.Company.name,
        excerpt = faker.Lorem.paragraph(5),
        textBody = faker.Lorem.paragraphs(10).mkString("\n"),
        htmlBody = faker.Lorem.paragraphs(10).mkString("\n"),
        status = "proposal",
        isArchived = "false",
        createdAt = now,
        updatedAt = now,
        id = None,
        startedAt = None,
        finishedAt = None
      ),
      _query += PostEntity(
        authorId = 1,
        title = faker.Company.name,
        excerpt = faker.Lorem.paragraph(5),
        textBody = faker.Lorem.paragraphs(10).mkString("\n"),
        htmlBody = faker.Lorem.paragraphs(10).mkString("\n"),
        status = "proposal",
        isArchived = "false",
        createdAt = now,
        updatedAt = now,
        id = None,
        startedAt = None,
        finishedAt = None
      )

    )

    val setupFuture = _db.run(setup)

    setupFuture.onSuccess {
      case s => println("success: ", s)
    }

    setupFuture.onFailure {
      case s => println("failed", s)
    }


  }
}
