package models

import slick.driver.MySQLDriver.api._


class Menu(tag: Tag) extends Table[MenuEntity](tag, "menu") {

  def id = column[Int]("menu_id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name", O.Length(32), O.Default(""))

  def content = column[String]("content", O.SqlType("TEXT"))

  def isEnabled = column[String]("is_enabled", O.SqlType("ENUM('true', 'false')"), O.Default("true"))

  def isArchived = column[String]("is_archived", O.SqlType("ENUM('true', 'false')"), O.Default("false"))

  //def idx = index("idx_a", (name), unique = true)

  def * =
    (
      id,
      name,
      content,
      isEnabled,
      isArchived) <>
      ((MenuEntity.apply _).tupled, MenuEntity.unapply)
}

case class MenuEntity
(
  id: Int,
  name: String,
  content: String,
  isEnabled: String,
  isArchived: String
  )

