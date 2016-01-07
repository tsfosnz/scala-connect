package service.team

import java.sql.SQLTimeoutException
import java.text.SimpleDateFormat

import models.{TeamMember, TeamMemberEntity}
import service.member.MemberIo
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import faker._


object TeamMemberIo {

  // all these code will go primary constructor, and run
  // automatically when object created

  val _query: TableQuery[TeamMember] = TableQuery[TeamMember]
  val _db: Database = Database.forConfig("mydb")


  def getMemberBy(teamId: Int) = {


    val query = for {

      (r, m) <- TeamMemberIo._query join MemberIo._query on (_.memberId === _.id)
      if r.teamId === teamId

    } yield m

    //val r = query.result.statements.head
    //println(r)

    val result = _db.run(query.drop(1).take(20).result)

    result

  }

  def add(teamId: Int, memberId: Int) {


    println(memberId)



    val action = DBIO.seq(

      _query += TeamMemberEntity(id = None, teamId = teamId, memberId = memberId)

    )



    //val sql = _query.insertStatement

    // here we can see the pure sql from query
    // println(sql)
    // _query += TeamMemberEntity(id = None, teamId = teamId, memberId = memberId)

    _db.run(action)


  }

  def remove(memberId: Int) = {


    val query = _query.sortBy(_.memberId.desc)

    // instead
    // DELETE FROM team WHERE created_at in
    // (SELECT created_at FROM team ORDER BY created at LIMIT 0, 1)

    // not supported

    val action = query.take(1).drop(200).delete
    val result = _db.run(action)

    val sql = action.statements.head

    println(sql)

    result

  }


  /**
   * initialize the table, create its schema, update its schema
   */
  def initialize = {

    val setup = DBIO.seq(

      //_query.schema.drop,
      //_query.schema.create


      //_query += TeamMember(101, "Acme, Inc."),
      //_query += TeamMember(49, "Superior Coffee"),
      //_query += TeamMember(150, "The High Ground")

    )

    val setupFuture = _db.run(setup)

    setupFuture


  }

}
