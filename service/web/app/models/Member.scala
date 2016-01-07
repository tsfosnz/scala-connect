package models

import slick.driver.MySQLDriver.api._

class Member(tag: Tag) extends Table[MemberEntity](tag, "member") {

  def id = column[Option[Int]]("member_id", O.PrimaryKey, O.AutoInc)

  def username = column[String]("username", O.Length(128), O.Default(""))

  def email = column[String]("email", O.Length(128), O.Default(""))

  def password = column[String]("password", O.Length(128), O.Default(""))

  def firstName = column[String]("first_name", O.Length(128), O.Default(""))

  def lastName = column[String]("last_name", O.Length(128), O.Default(""))

  def displayName = column[String]("display_name", O.Length(128), O.Default(""))

  def description = column[String]("description", O.Length(512), O.Default(""))

  def createdAt = column[String]("created_at", O.SqlType("DateTime"))

  def updatedAt = column[String]("updated_at", O.SqlType("DateTime"))

  def * =
    (
      id,
      username,
      email,
      password,
      firstName,
      lastName,
      displayName,
      description,
      createdAt,
      updatedAt) <>
      ((MemberEntity.apply _).tupled, MemberEntity.unapply)
}

case class MemberEntity
(
  id: Option[Int],
  username: String,
  email: String,
  password: String,
  firstName: String,
  lastName: String,
  displayName: String,
  description: String,
  createdAt: String,
  updatedAt: String
  )

