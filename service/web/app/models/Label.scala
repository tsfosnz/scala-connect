package models

import slick.driver.MySQLDriver.api._

class Label(tag: Tag) extends Table[LabelEntity](tag, "label") {

  def id = column[Int]("label_id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name", O.Length(128), O.Default(""))

  def createdAt = column[String]("created_at", O.SqlType("DateTime"))

  def updatedAt = column[String]("updated_at", O.SqlType("DateTime"))

  def * =
    (
      id,
      name,
      createdAt,
      updatedAt) <>
      ((LabelEntity.apply _).tupled, LabelEntity.unapply)
}

case class LabelEntity
(
  id: Int,
  name: String,
  createdAt: String,
  updatedAt: String
  )

