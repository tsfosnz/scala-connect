package service.team

import java.sql.SQLTimeoutException
import java.text.SimpleDateFormat

import models.{TeamFullEntity, MemberEntity, Team, TeamEntity}
import service.member.MemberServ
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import faker._


object TeamServ {

  lazy val query: TableQuery[Team] = TableQuery[Team]
  lazy val db: Database = Database.forConfig("mydb")

  def test = {

    TeamMemberServ.remove(1)

  }

  /**
   *
   * Get team ++ member, with page and count limit,
   * and it should be (team, team, ...), for each team,
   * team.member = (member, member...)
   *
   * @param page
   * @param count
   * @return
   */
  def all(page: Int, count: Int) = {


    try {


      val q = query.drop(page).take(count)

      val action = q.result

      val sql = q.result.statements.head

      //println(sql)

      val result = db.run(action)


      val withMember = for {

        r <- result

      } yield {

          val b = for {

            t <- r

          } yield {

            val member = TeamMemberServ.getMemberBy(t.id.get)

            for {

              m <- member

            } yield TeamFullEntity(team = t, member = m)


            //Future.successful(b)

          }

          Future.sequence(b)

        }

      withMember

    }

    catch {
      case err: Throwable => null
    }

  }

  def one(id: Int): Future[Seq[TeamEntity]] = {

    // first lets define a SQL


    val q = query.filter(_.id === id)
    val sql = q.result.statements.head

    println(sql)

    val action = q.result
    val result: Future[Seq[TeamEntity]] = db.run(action)

    result

  }

  def remove = {

    // first lets define a SQL


    val q = query.take(1).sortBy(_.createdAt.desc)

    val action = q.delete
    val result = db.run(action)

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

      query.map(
        model => (
          model.name,
          model.description,
          model.createdAt,
          model.updatedAt

          )) +=(
        data("name"),
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


    val dt: java.util.Date = new java.util.Date();

    val sdf: SimpleDateFormat =
      new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    val now: String = sdf.format(dt);

    // how to add to model member according to data
    // team[Map.Key] = Map.Value?

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

  def addMember(teamId: Int, memberId: Int): Unit = {

    /*
    for {

      t <- team
      m <- member

    } yield {

      TeamMember.add(t.id.get, m.id.get)

    }*/

    TeamMemberServ.add(teamId, memberId)

  }

  /**
   * initialize the table, create its schema, update its schema
   */
  def initialize = {

    val setup = DBIO.seq(

      //query.schema.drop,
      //query.schema.create


      //query += Team(101, "Acme, Inc."),
      //query += Team(49, "Superior Coffee"),
      //query += Team(150, "The High Ground")

    )

    val result = db.run(setup)

    result


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

      // query,

      query += TeamEntity(
        name = faker.Company.name,
        description = faker.Company.name,
        createdAt = now,
        updatedAt = now,
        id = None
      ),
      query += TeamEntity(
        name = faker.Company.name,
        description = faker.Company.name,
        createdAt = now,
        updatedAt = now,
        id = None
      )

    )

    val result = db.run(setup)

    result


  }
}
