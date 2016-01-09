package models

import slick.driver.MySQLDriver.api._

class Post(tag: Tag) extends Table[PostEntity](tag, "post") {

  def id = column[Int]("post_id", O.PrimaryKey, O.AutoInc)

  def urlKey = column[String]("url_key", O.Length(255), O.Default(""))

  def authorId = column[Int]("author_id", O.Default(0))

  def postType = column[String]("type", O.Length(256), O.Default(""))

  def title = column[String]("title", O.Length(256), O.Default(""))

  def excerpt = column[String]("excerpt", O.Length(512), O.Default(""))

  def textBody = column[String]("text_body", O.SqlType("TEXT"))

  def htmlBody = column[String]("html_body", O.SqlType("TEXT"))

  def status = column[String]("status", O.SqlType("CHAR"), O.Length(32), O.Default(""))

  def isArchived = column[String]("is_archived", O.SqlType("ENUM('true', 'false')"), O.Default("false"))

  def createdAt = column[String]("created_at", O.SqlType("DateTime"))

  def updatedAt = column[String]("updated_at", O.SqlType("DateTime"))

  def * =
    (
      id,
      authorId,
      postType,
      title,
      excerpt,
      textBody,
      htmlBody,
      status,
      isArchived,
      createdAt,
      updatedAt) <>
      ((PostEntity.apply _).tupled, PostEntity.unapply)
}

case class PostEntity
(
  id: Int,
  authorId: Int,
  postType: String,
  title: String,
  excerpt: String,
  textBody: String,
  htmlBody: String,
  status: String,
  isArchived: String,
  createdAt: String,
  updatedAt: String
  )

