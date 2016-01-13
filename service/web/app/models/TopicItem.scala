package models

import slick.driver.MySQLDriver.api._

class TopicItem(tag: Tag) extends Table[TopicItemEntity](tag, "topic_item") {

  def id = column[Int]("topic_item_id", O.PrimaryKey, O.AutoInc)

  def topicId = column[Int]("topic_id")

  def itemId = column[Int]("item_id")
  
  def itemType = column[String]("item_type", O.SqlType("VARCHAR"), O.Length(32))

  def * =
    (
      id,
      topicId,
      itemId,
      itemType) <>
      ((TopicItemEntity.apply _).tupled, TopicItemEntity.unapply)
}

case class TopicItemEntity
(
  id: Int,
  topicId: Int,
  itemId: Int,
  itemType: String
  )
