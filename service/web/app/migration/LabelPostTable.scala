package migration

import core.MigrationTable
import models.LabelPost
import slick.driver.MySQLDriver.api._

object LabelPostTable extends MigrationTable[LabelPost]{

  //type T = LabelPost
  lazy val query = TableQuery[LabelPost]
  lazy val db = Database.forConfig("mydb")

  def initialize(drop: Boolean = false) = init(query, db)(drop)


  /**
   * populate the data into the table
   */
  def populate(labelId: Int, postId: Int) = {


    val setup = DBIO.seq(

      query.map {

        m => (
          m.labelId,
          m.postId
          )
      } +=(
        labelId,
        postId
        )

    )

    val result = db.run(setup)

    result


  }
}
