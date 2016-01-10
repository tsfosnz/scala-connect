package models

import slick.driver.MySQLDriver.api._

class Comment(tag: Tag) extends Table[CommentEntity](tag, "comment") {

  def id = column[Int]("comment_id", O.PrimaryKey, O.AutoInc)

  def parentId = column[Int]("parent_id")

  def itemId = column[Int]("item_id")

  def level = column[Int]("level")

  def urlKey = column[String]("url_key", O.Length(255), O.Default(""))

  def authorId = column[Int]("author_id", O.Default(0))

  def commentType = column[String]("type", O.Length(256), O.Default(""))

  def textBody = column[String]("text_body", O.SqlType("TEXT"))

  def htmlBody = column[String]("html_body", O.SqlType("TEXT"))

  def status = column[String]("status", O.SqlType("CHAR"), O.Length(32), O.Default(""))

  def isArchived = column[String]("is_archived", O.SqlType("ENUM('true', 'false')"), O.Default("false"))

  def createdAt = column[String]("created_at", O.SqlType("DateTime"))

  def updatedAt = column[String]("updated_at", O.SqlType("DateTime"))

  def * =
    (
      id,
      parentId,
      itemId,
      level,
      authorId,
      commentType,
      textBody,
      htmlBody,
      status,
      isArchived,
      createdAt,
      updatedAt) <>
      ((CommentEntity.apply _).tupled, CommentEntity.unapply)
}

case class CommentEntity
(
  id: Int,
  parentId: Int,
  itemId: Int,
  level: Int,
  authorId: Int,
  commentType: String,
  textBody: String,
  htmlBody: String,
  status: String,
  isArchived: String,
  createdAt: String,
  updatedAt: String
  )

