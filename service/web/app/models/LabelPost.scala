package models

import slick.driver.MySQLDriver.api._

class LabelPost(tag: Tag) extends Table[LabelPostEntity](tag, "label_post") {

  def id = column[Int]("label_id", O.PrimaryKey, O.AutoInc)

  def labelId = column[Int]("label_id")

  def postId = column[Int]("post_id")

  def * =
    (
      id,
      labelId,
      postId) <>
      ((LabelPostEntity.apply _).tupled, LabelPostEntity.unapply)
}

case class LabelPostEntity
(
  id: Int,
  labelId: Int,
  postId: Int
  )

