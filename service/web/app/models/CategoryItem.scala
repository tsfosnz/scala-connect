package models

import slick.driver.MySQLDriver.api._

class CategoryItem(tag: Tag) extends Table[CategoryItemEntity](tag, "category_item") {

  def id = column[Int]("category_item_id", O.PrimaryKey, O.AutoInc)

  def categoryId = column[Int]("category_id")

  def itemId = column[Int]("item_id")
  
  def itemType = column[String]("item_type", O.SqlType("VARCHAR"), O.Length(32))

  def * =
    (
      id,
      categoryId,
      itemId,
      itemType) <>
      ((CategoryItemEntity.apply _).tupled, CategoryItemEntity.unapply)
}

case class CategoryItemEntity
(
  id: Int,
  categoryId: Int,
  itemId: Int,
  itemType: String
  )
