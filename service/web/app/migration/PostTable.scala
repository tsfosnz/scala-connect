package migration

import java.text.SimpleDateFormat

import core.MigrationTable
import models.Post
import service.post.PostServ
import slick.driver.MySQLDriver.api._

object PostTable extends MigrationTable[Post]{

  lazy val query = PostServ.query
  lazy val db = PostServ.db

  def initialize(drop:Boolean = false) = init(query, db)(drop)

  /**
   * populate the data into the table
   */
  def populate = {

    val dt: java.util.Date = new java.util.Date()

    val sdf: SimpleDateFormat =
      new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    val now: String = sdf.format(dt)


    val setup = DBIO.seq(

      // query,

      query.map {

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

    val result = db.run(setup)

    result
  }

}
