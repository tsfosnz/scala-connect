package models

import core.DbQuery
import models.base.BaseDateTime
import slick.driver.MySQLDriver.api._

class Comment(tag: Tag) extends BaseDateTime[CommentEntity](tag, "comment") {

  def id = column[Int]("comment_id", O.PrimaryKey, O.AutoInc)

  def parentId = column[Int]("parent_id", O.Default(0))

  def itemId = column[Int]("item_id", O.Default(0))

  def level = column[Int]("level", O.Default(0))

  def authorId = column[Int]("author_id", O.Default(0))

  def commentType = column[String]("type", O.Length(256), O.Default(""))

  def textBody = column[String]("text_body", O.SqlType("TEXT"))

  def htmlBody = column[String]("html_body", O.SqlType("TEXT"))

  def status = column[String]("status", O.SqlType("CHAR"), O.Length(32), O.Default(""))

  def isArchived = column[String]("is_archived", O.SqlType("ENUM('true', 'false')"), O.Default("false"))

  def replyCount = column[Int]("count_reply", O.Default(0))

  def readCount = column[Int]("count_read", O.Default(0))

  def likeCount = column[Int]("count_like", O.Default(0))

  def dislikeCount = column[Int]("count_dislike", O.Default(0))

  //def createdAt = column[String]("created_at", O.SqlType("DateTime"))

  //def updatedAt = column[String]("updated_at", O.SqlType("DateTime"))

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
      replyCount,
      readCount,
      likeCount,
      dislikeCount,
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
  replyCount: Int,
  readCount: Int,
  likeCount: Int,
  dislikeCount: Int,
  createdAt: String,
  updatedAt: String
  )

object CommentQuery extends DbQuery[Comment](
  (tag: Tag) => new Comment(tag))  {


}


