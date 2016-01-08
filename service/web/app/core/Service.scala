package core


import models.{Label, Post}
import slick.driver.MySQLDriver.api._
import slick.lifted.AbstractTable

import scala.reflect.ClassTag

case class Ab(t: Int, m: Int, n: Int)
class A(tag: Tag) extends Table[Ab](tag, "label_post") {

  def id = column[Int]("label_post_id", O.PrimaryKey, O.AutoInc)

  def labelId = column[Int]("label_id")

  def postId = column[Int]("post_id")

  def * =
    (
      id,
      labelId,
      postId) <>
      ((Ab.apply _).tupled, Ab.unapply)
}


class Service[T <: Table[_]](path: String, cons: Tag => T){

  //class A extends slick.lifted.TableQuery[T]


  //lazy val query = TableQuery[Label]
  lazy val db = Database.forConfig(path)

  def query = TableQuery[T](cons)
}
