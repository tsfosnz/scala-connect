package core

import slick.driver.MySQLDriver.api._

trait MigrationTable[T <: Table[_]] {

  /**
   * initialize the table, create its schema, update its schema
   */
  def init(query: TableQuery[T], db: Database)(drop: Boolean = false) = {


    val withDrop= List(query.schema.drop, query.schema.create)
    val onlyCreate= List(query.schema.create)


    val setup = if (drop)
      DBIO.seq(withDrop: _*)
    else
      DBIO.seq(onlyCreate: _*)

    val result = db.run(setup)

    result

  }

}
