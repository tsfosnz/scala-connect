package migration

import java.text.SimpleDateFormat

import core.MigrationTable
import models.{CommentQuery, Comment}
import slick.driver.MySQLDriver.api._

object CommentTable extends MigrationTable[Comment]{

  val query = CommentQuery.query
  val db = CommentQuery.db

  def initialize(drop:Boolean = false) = init(query, db)(drop)

  /**
   * populate the data into the table
   */
  def populate(postId: Int) = {

    val dt: java.util.Date = new java.util.Date()

    val sdf: SimpleDateFormat =
      new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    val now: String = sdf.format(dt)


    val setup = DBIO.seq(

      // query,

      query.map {

        m => (
          m.itemId,
          m.authorId,
          m.textBody,
          m.htmlBody,
          m.createdAt,
          m.updatedAt
          )
      } +=(
        postId,
        (Math.random() * 100).toInt,
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
