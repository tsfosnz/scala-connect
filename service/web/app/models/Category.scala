package models

import models.base.BaseDateTime
import slick.driver.MySQLDriver.api._


class Category(tag: Tag) extends Table[CategoryEntity](tag, "category") {

  def id = column[Int]("category_id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name", O.Length(128), O.Default(""))

  def createdAt = column[String]("created_at", O.SqlType("DateTime"))

  def updatedAt = column[String]("updated_at", O.SqlType("DateTime"))

  def * =
    (
      id,
      name,
      createdAt,
      updatedAt) <>
      ((CategoryEntity.apply _).tupled, CategoryEntity.unapply)
}

case class CategoryEntity
(
  id: Int,
  name: String,
  createdAt: String,
  updatedAt: String
  )

