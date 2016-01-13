package service.admin

import java.text.SimpleDateFormat

import core.Service
import models.Administrator
import slick.driver.MySQLDriver.api._

import scala.concurrent.Future


object AdministratorServ  extends Service[Administrator](
  "mydb",
  (tag: Tag) => new Administrator(tag)) {

  def test() {


  }

  def all(page: Int, count: Int) = {

    try {

      val q = query.drop(page).take(count)
      val sql = q.result.statements.head

      println(sql)

      val action = q.result
      val result = db.run(action)


      //throw new Throwable("Wrong!!")

      //db.close

      result
    }

    catch {
      case err: Throwable => null
    }

  }

  def one(id: Int) = {

    // first lets define a SQL


    val q = query.filter(_.id === id)
    val sql = q.result.statements.head

    println(sql)

    val action = q.result
    val result = db.run(action)

    result

  }

  def remove = {

    // first lets define a SQL


    val q = query.take(1).sortBy(_.createdAt.desc)

    val action = q.delete
    val result = db.run(action)

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

      query.map(
        m => (
          m.username,
          m.introduction,
          m.createdAt,
          m.updatedAt

          )) +=(
        data("username"),
        data("description"),
        now,
        now
        ))


    val sql = query.insertStatement

    // here we can see the pure sql from q
    println(sql)

    val result = db.run(action)

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
        query.map(

          model => for {

            m <- model
            if data.keySet.exists(m)

          } yield m

        )
        */
    )


    val sql = query.insertStatement

    // here we can see the pure sql from q
    println(sql)

    val result = db.run(action)

    result

  }

}
