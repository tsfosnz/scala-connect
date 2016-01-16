package models

import core.DbQuery
import models.base.{BaseDateTime}
import slick.driver.MySQLDriver.api._

class Post(tag: Tag) extends BaseDateTime[PostEntity](tag, "post")  {

  def id = column[Int]("post_id", O.PrimaryKey, O.AutoInc)

  def authorId = column[Int]("author_id", O.Default(0))

  def postType = column[String]("type", O.Length(256), O.Default(""))

  def urlKey = column[String]("url_key", O.Length(255), O.Default(""))

  def title = column[String]("title", O.Length(256), O.Default(""))

  def excerpt = column[String]("excerpt", O.Length(512), O.Default(""))

  def textBody = column[String]("text_body", O.SqlType("TEXT"))

  def htmlBody = column[String]("html_body", O.SqlType("TEXT"))

  def status = column[String]("status", O.SqlType("CHAR"), O.Length(32), O.Default(""))

  def isArchived = column[String]("is_archived", O.SqlType("ENUM('true', 'false')"), O.Default("false"))

  def commentCount = column[Int]("count_comment", O.Default(0))

  def readCount = column[Int]("count_read", O.Default(0))

  def likeCount = column[Int]("count_like", O.Default(0))

  def dislikeCount =  column[Int]("count_dislike", O.Default(0))

  //def idx = index("idx_a", (urlKey), unique = false)

  //def createdAt = column[String]("created_at", O.SqlType("DateTime"))

  //def updatedAt = column[String]("updated_at", O.SqlType("DateTime"))

  def * =
    (
      id,
      authorId,
      postType,
      urlKey,
      title,
      excerpt,
      textBody,
      htmlBody,
      status,
      isArchived,
      commentCount,
      readCount,
      likeCount,
      dislikeCount,
      createdAt,
      updatedAt) <>
      ((PostEntity.apply _).tupled, PostEntity.unapply)
}

case class PostEntity
(
  id: Int,
  authorId: Int,
  postType: String,
  urlKey: String,
  title: String,
  excerpt: String,
  textBody: String,
  htmlBody: String,
  status: String,
  isArchived: String,
  commentCount: Int,
  readCount: Int,
  likeCount: Int,
  dislikeCount: Int,
  createdAt: String,
  updatedAt: String
  )

object PostQuery extends DbQuery[Post](
  "mydb",
  (tag: Tag) => new Post(tag))  {


}

