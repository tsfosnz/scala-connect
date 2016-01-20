package models

import core.DbQuery
import models.base.BaseDateTime
import slick.driver.MySQLDriver.api._

class Label(tag: Tag) extends BaseDateTime[LabelEntity](tag, "label") {

  def id = column[Int]("label_id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name", O.Length(128), O.Default(""))

  //def idx = index("idx_a", (name), unique = true)

  //def createdAt = column[String]("created_at", O.SqlType("DateTime"))

  //def updatedAt = column[String]("updated_at", O.SqlType("DateTime"))

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

object LabelQuery extends DbQuery[Label](
  (tag: Tag) => new Label(tag))  {


}


