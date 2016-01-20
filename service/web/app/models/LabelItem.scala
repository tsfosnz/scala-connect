package models

import core.DbQuery
import slick.driver.MySQLDriver.api._

class LabelItem(tag: Tag) extends Table[LabelItemEntity](tag, "label_item") {

  def id = column[Int]("label_item_id", O.PrimaryKey, O.AutoInc)

  def labelId = column[Int]("label_id")

  def itemId = column[Int]("item_id")
  
  def itemType = column[String]("item_type", O.SqlType("VARCHAR"), O.Length(32))

  def * =
    (
      id,
      labelId,
      itemId,
      itemType) <>
      ((LabelItemEntity.apply _).tupled, LabelItemEntity.unapply)
}

case class LabelItemEntity
(
  id: Int,
  labelId: Int,
  itemId: Int,
  itemType: String
  )

object LabelItemQuery extends DbQuery[LabelItem](
  (tag: Tag) => new LabelItem(tag))  {


}

