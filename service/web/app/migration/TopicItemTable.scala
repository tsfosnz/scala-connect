package migration

import core.MigrationTable
import models._
import slick.driver.MySQLDriver.api._

object TopicItemTable extends MigrationTable[TopicItem]{

  //type T = LabelPost
  lazy val query = TopicItemQuery.query
  lazy val db = TopicItemQuery.db

  def initialize(drop: Boolean = false) = init(query, db)(drop)


  /**
   * populate the data into the table
   */
  def populate(categoryId: Int, postId: Int, itemType: String) = {


    val setup = DBIO.seq(

      query.map {

        m => (
          m.topicId,
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
