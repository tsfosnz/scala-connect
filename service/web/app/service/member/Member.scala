package service.member

import java.text.SimpleDateFormat

import models.{Member, MemberEntity}
import slick.driver.MySQLDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


object Member {

  lazy val _query: TableQuery[Member] = TableQuery[Member]
  lazy val _db: Database = Database.forConfig("mydb")

  def test = {


    val data: Map[String, Any] = Map(
      "managerId" -> 1,
      "name" -> "test"

    )
  }

  def all(page: Int, count: Int): Future[Seq[MemberEntity]] = {

    try {

      val query = _query.drop(page).take(count)
      val sql = query.result.statements.head

      //println(sql)

      val action = query.result
      val result: Future[Seq[MemberEntity]] = _db.run(action)


      //throw new Exception("Wrong!!")

      //_db.close

      result
    }

    catch {
      case err: Exception => null
    }

  }

  def one(id: Int): Future[Seq[MemberEntity]] = {

    // first lets define a SQL


    val query = _query.filter(_.id === id)
    val sql = query.result.statements.head

    println(sql)

    val action = query.result
    val result: Future[Seq[MemberEntity]] = _db.run(action)

    result

  }

  def remove = {

    // first lets define a SQL


    val query = _query.take(1).sortBy(_.createdAt.desc)

    val action = query.delete
    val result = _db.run(action)

    val sql = action.statements.head

    println(sql)

    //result

  }

  def add(data: Map[String, String]): Future[Unit] = {


    val dt: java.util.Date = new java.util.Date();
    val sdf: SimpleDateFormat =
      new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    val now: String = sdf.format(dt);

    val action = DBIO.seq(

      _query.map(
        model => (
          model.username,
          model.description,
          model.createdAt,
          model.updatedAt

          )) +=(
        data("username"),
        data("description"),
        now,
        now
        ))


    val sql = _query.insertStatement

    // here we can see the pure sql from query
    println(sql)

    val result = _db.run(action)

    result

  }

  def update(data: Map[String, String]): Future[Unit] = {


    val dt: java.util.Date = new java.util.Date();

    val sdf: SimpleDateFormat =
      new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    val now: String = sdf.format(dt);

    // how to add to model member according to data
    // member[Map.Key] = Map.Value?

    val action = DBIO.seq(

      /*
        _query.map(

          model => for {

            m <- model
            if data.keySet.exists(m)

          } yield m

        )
        */
    )


    val sql = _query.insertStatement

    // here we can see the pure sql from query
    println(sql)

    val result = _db.run(action)

    result

  }


  /**
   * initialize the table, create its schema, update its schema
   */
  def initialize = {

    val setup = DBIO.seq(

      //_query.schema.drop,
      //_query.schema.create


      //_query += Member(101, "Acme, Inc."),
      //_query += Member(49, "Superior Coffee"),
      //_query += Member(150, "The High Ground")

    )

    val setupFuture = _db.run(setup)

    setupFuture.onSuccess {
      case s => println("success: ", s)
    }

    setupFuture.onFailure {
      case s => println("failed", s)
    }

    _db.close()


  }

  /**
   * populate the data into the table
   */
  def populate = {

    val dt: java.util.Date = new java.util.Date();

    val sdf: SimpleDateFormat =
      new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    val now: String = sdf.format(dt);


    val setup = DBIO.seq(

      // _query,

      _query += MemberEntity(
        username = faker.Name.name,
        email = faker.Internet.email,
        password = "",
        firstName = faker.Name.first_name,
        lastName = faker.Name.last_name,
        displayName = faker.Name.name,
        description = faker.Company.name,
        createdAt = now,
        updatedAt = now,
        id = None
      ),
      _query += MemberEntity(
        username = faker.Name.name,
        email = faker.Internet.email,
        password = "",
        firstName = faker.Name.first_name,
        lastName = faker.Name.last_name,
        displayName = faker.Name.name,
        description = faker.Company.name,
        createdAt = now,
        updatedAt = now,
        id = None
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
