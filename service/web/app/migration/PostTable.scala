package migration

import java.text.SimpleDateFormat

import core.MigrationTable
import models.Post
import slick.driver.MySQLDriver.api._

object PostTable extends MigrationTable[Post]{

  lazy val _query: TableQuery[Post] = TableQuery[Post]
  lazy val _db: Database = Database.forConfig("mydb")

  def initialize(drop:Boolean = false) = init(_query, _db)(drop)

  /**
   * populate the data into the table
   */
  def populate = {

    val dt: java.util.Date = new java.util.Date()

    val sdf: SimpleDateFormat =
      new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    val now: String = sdf.format(dt)


    val setup = DBIO.seq(

      // _query,

      _query.map {

        m => (
          m.authorId,
          m.title,
          m.excerpt,
          m.textBody,
          m.htmlBody,
          m.createdAt,
          m.updatedAt
          )
      } +=(
        (Math.random() * 100).toInt,
        faker.Lorem.sentence(16),
        faker.Lorem.paragraph(3),
        faker.Lorem.paragraphs(10).mkString("\n"),
        faker.Lorem.paragraphs(10).mkString("\n"),
        now,
        now
        )

    )

    val result = _db.run(setup)

    result
  }

}
