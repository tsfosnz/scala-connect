package migration

import core.MigrationTable
import models._
import slick.driver.MySQLDriver.api._

object LabelItemTable extends MigrationTable[LabelItem]{

  //type T = LabelPost
  lazy val query = LabelItemQuery.query
  lazy val db = LabelItemQuery.db

  def initialize(drop: Boolean = false) = init(query, db)(drop)


  /**
   * populate the data into the table
   */
  def populate(labelId: Int, postId: Int, itemType: String) = {


    val setup = DBIO.seq(

      query.map {

        m => (
          m.labelId,
          m.itemId,
          m.itemType
          )
      } +=(
        labelId,
        postId,
        itemType
        )

    )

    val result = db.run(setup)

    result


  }
}
