package migration

import core.MigrationTable
import models.LabelPost
import slick.driver.MySQLDriver.api._

object LabelPostTable extends MigrationTable[LabelPost]{

  lazy val _query: TableQuery[LabelPost] = TableQuery[LabelPost]
  lazy val _db: Database = Database.forConfig("mydb")

  def initialize(drop: Boolean = false) = init(_query, _db)(drop)


  /**
   * populate the data into the table
   */
  def populate(labelId: Int, postId: Int) = {


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

    val result = _db.run(setup)

    result


  }
}
