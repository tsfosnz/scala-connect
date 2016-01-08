package service.member

import java.text.SimpleDateFormat

import models.Member
import slick.driver.MySQLDriver.api._

import scala.concurrent.Future


object MemberServ {

  lazy val _query: TableQuery[Member] = TableQuery[Member]
  lazy val _db: Database = Database.forConfig("mydb")

  def test() {


  }

  def all(page: Int, count: Int) = {

    try {

      val query = _query.drop(page).take(count)
      val sql = query.result.statements.head

      println(sql)

      val action = query.result
      val result = _db.run(action)


      //throw new Throwable("Wrong!!")

      //_db.close

      result
    }

    catch {
      case err: Throwable => null
    }

  }

  def one(id: Int) = {

    // first lets define a SQL


    val query = _query.filter(_.id === id)
    val sql = query.result.statements.head

    println(sql)

    val action = query.result
    val result = _db.run(action)

    result

  }

  def remove = {

    // first lets define a SQL


    val query = _query.take(1).sortBy(_.createdAt.desc)

    val action = query.delete
    val result = _db.run(action)

    val sql = action.statements.head

    println(sql)

    result

  }

  def add(data: Map[String, String]) = {


    val dt: java.util.Date = new java.util.Date()
    val sdf: SimpleDateFormat =
      new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    val now: String = sdf.format(dt)

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


    val dt: java.util.Date = new java.util.Date()

    val sdf: SimpleDateFormat =
      new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    val now: String = sdf.format(dt)

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

}
