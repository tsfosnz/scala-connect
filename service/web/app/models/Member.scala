package models

import core.DbQuery
import models.base.BaseDateTime
import slick.driver.MySQLDriver.api._

class Member(tag: Tag) extends BaseDateTime[MemberEntity](tag, "member") {

  def id = column[Int]("member_id", O.PrimaryKey, O.AutoInc)

  def username = column[String]("username", O.Length(128), O.Default(""))

  def email = column[String]("email", O.Length(128), O.Default(""))

  def password = column[String]("password", O.Length(128), O.Default(""))

  def firstName = column[String]("first_name", O.Length(128), O.Default(""))

  def lastName = column[String]("last_name", O.Length(128), O.Default(""))

  def displayName = column[String]("display_name", O.Length(128), O.Default(""))

  def isActive = column[String]("is_active", O.SqlType("ENUM('true', 'false')"), O.Default("false"))

  def introduction = column[String]("introduction", O.Length(512), O.Default(""))

  def icon = column[String]("icon", O.Length(256), O.Default(""))

  //def idx = index("idx_a", (username, email), unique = true)

  //def createdAt = column[String]("created_at", O.SqlType("DateTime"))

  //def updatedAt = column[String]("updated_at", O.SqlType("DateTime"))

  def * =
    (
      id,
      username,
      email,
      password,
      firstName,
      lastName,
      displayName,
      introduction,
      icon,
      createdAt,
      updatedAt) <>
      ((MemberEntity.apply _).tupled, MemberEntity.unapply)
}

case class MemberEntity
(
  id: Int,
  username: String,
  email: String,
  password: String,
  firstName: String,
  lastName: String,
  displayName: String,
  introduction: String,
  icon: String,
  createdAt: String,
  updatedAt: String
  )

object MemberQuery extends DbQuery[Member](
  (tag: Tag) => new Member(tag)) {


}


