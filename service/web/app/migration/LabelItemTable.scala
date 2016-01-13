package migration

import core.MigrationTable
import models.LabelItem
import service.label.LabelItemServ
import slick.driver.MySQLDriver.api._

object LabelItemTable extends MigrationTable[LabelItem]{

  //type T = LabelPost
  lazy val query = LabelItemServ.query
  lazy val db = LabelItemServ.db

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
