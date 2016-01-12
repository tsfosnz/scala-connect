package models.base


import slick.driver.MySQLDriver.api._


private[models] abstract class BaseDateTime[T](tag: Tag, tableName: String) extends Table[T](tag, tableName) {


  def createdAt = column[String]("created_at", O.SqlType("DateTime"))
  def updatedAt = column[String]("updated_at", O.SqlType("DateTime"))

}

