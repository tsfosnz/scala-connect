package models.base

import slick.driver.MySQLDriver.api._

abstract class BaseSketch[T](tag: Tag, tableName: String) extends Table[T](tag, tableName)

