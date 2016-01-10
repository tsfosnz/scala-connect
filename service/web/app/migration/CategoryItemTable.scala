package migration

import core.MigrationTable
import models.CategoryItem
import service.category.CategoryItemServ
import slick.driver.MySQLDriver.api._

object CategoryItemTable extends MigrationTable[CategoryItem]{

  //type T = LabelPost
  lazy val query = CategoryItemServ.query
  lazy val db = CategoryItemServ.db

  def initialize(drop: Boolean = false) = init(query, db)(drop)


  /**
   * populate the data into the table
   */
  def populate(categoryId: Int, postId: Int, itemType: String) = {


    val setup = DBIO.seq(

      query.map {

        m => (
          m.categoryId,
          m.itemId,
          m.itemType
          )
      } +=(
        categoryId,
        postId,
        itemType
        )

    )

    val result = db.run(setup)

    result


  }
}
