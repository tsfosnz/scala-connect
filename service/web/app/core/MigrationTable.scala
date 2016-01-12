package core

import slick.driver.MySQLDriver.api._

trait MigrationTable[T <: Table[_]] {

  /**
   * initialize the table, create its schema, update its schema
   */
  def init(query: TableQuery[T], db: Database)(drop: Boolean = false) = {


    val op = drop match {

      case true =>  List(query.schema.drop, query.schema.create)
      case _ => List(query.schema.create)
    }


    val setup = DBIO.seq(op: _*)

    val result = db.run(setup)

    result

  }

  /**
   * initialize the table, create its schema, update its schema
   */
  def init = {



  }

}
