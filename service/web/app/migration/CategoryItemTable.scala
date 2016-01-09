package migration

import core.MigrationTable
import models.CategoryItem
import slick.driver.MySQLDriver.api._

object CategoryItemTable extends MigrationTable[CategoryItem]{

  //type T = LabelPost
  lazy val query = TableQuery[CategoryItem]
  lazy val db = Database.forConfig("mydb")

  def initialize(drop: Boolean = false) = init(query, db)(drop)


  /**
   * populate the data into the table
   */
  def populate(labelId: Int, postId: Int) = {


    val setup = DBIO.seq(

      query.map {

        m => (
          m.categoryId,
          m.itemId
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
