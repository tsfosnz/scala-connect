package models

import slick.driver.MySQLDriver.api._

class Team(tag: Tag) extends Table[TeamEntity](tag, "team") {

  def id = column[Option[Int]]("team_id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name", O.Length(128), O.Default(""))

  def description = column[String]("description", O.Length(512), O.Default(""))

  def createdAt = column[String]("created_at", O.SqlType("DateTime"))

  def updatedAt = column[String]("updated_at", O.SqlType("DateTime"))

  def * =
    (
      id,
      name,
      description,
      createdAt,
      updatedAt) <>
      ((TeamEntity.apply _).tupled, TeamEntity.unapply)
}

case class TeamEntity
(
  id: Option[Int],
  name: String,
  description: String,
  createdAt: String,
  updatedAt: String
  )

