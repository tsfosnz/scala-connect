package service.team

import models.{TeamMember, TeamMemberEntity}
import service.member.MemberServ
import slick.driver.MySQLDriver.api._

object TeamMemberServ {

  // all these code will go primary constructor, and run
  // automatically when object created

  val query: TableQuery[TeamMember] = TableQuery[TeamMember]
  val db: Database = Database.forConfig("mydb")


  def getMemberBy(teamId: Int) = {


    val q = for {

      (r, m) <- TeamMemberServ.query join MemberServ.query on (_.memberId === _.id)
      if r.teamId === teamId

    } yield m

    //val r = q.result.statements.head
    //println(r)

    val result = db.run(q.drop(1).take(20).result)

    result

  }

  def add(teamId: Int, memberId: Int) {


    println(memberId)



    val action = DBIO.seq(

      query += TeamMemberEntity(id = None, teamId = teamId, memberId = memberId)

    )



    //val sql = query.insertStatement

    // here we can see the pure sql from q
    // println(sql)
    // query += TeamMemberEntity(id = None, teamId = teamId, memberId = memberId)

    db.run(action)


  }

  def remove(memberId: Int) = {


    val q = query.sortBy(_.memberId.desc)

    // instead
    // DELETE FROM team WHERE created_at in
    // (SELECT created_at FROM team ORDER BY created at LIMIT 0, 1)

    // not supported

    val action = q.take(1).drop(200).delete
    val result = db.run(action)

    val sql = action.statements.head

    println(sql)

    result

  }


  /**
   * initialize the table, create its schema, update its schema
   */
  def initialize = {

    val setup = DBIO.seq(

      //query.schema.drop,
      //query.schema.create


      //query += TeamMember(101, "Acme, Inc."),
      //query += TeamMember(49, "Superior Coffee"),
      //query += TeamMember(150, "The High Ground")

    )

    val result = db.run(setup)

    result


  }

}
