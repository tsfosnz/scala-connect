package models

import core.DbQuery
import models.base.BaseDateTime
import slick.driver.MySQLDriver.api._


class Topic(tag: Tag) extends BaseDateTime[TopicEntity](tag, "topic") {

  def id = column[Int]("topic_id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name", O.Length(128), O.Default(""))

  def isArchived = column[String]("is_archived", O.SqlType("ENUM('true', 'false')"), O.Default("false"))

  //def idx = index("idx_a", (name), unique = true)

  //def createdAt = column[String]("created_at", O.SqlType("DateTime"))

  //def updatedAt = column[String]("updated_at", O.SqlType("DateTime"))

  def * =
    (
      id,
      name,
      isArchived,
      createdAt,
      updatedAt) <>
      ((TopicEntity.apply _).tupled, TopicEntity.unapply)
}

case class TopicEntity
(
  id: Int,
  name: String,
  isArchived: String,
  createdAt: String,
  updatedAt: String
  )

object TopicQuery extends DbQuery[Topic](
  "mydb",
  (tag: Tag) => new Topic(tag)) {


}

