package core

import slick.driver.MySQLDriver.api._

class DbQuery[T <: Table[_]](foo: Tag => T) {


  /*

  // how about java reflection

  val clazz = Class.forName(name)
  def cons = (tag: Tag) => clazz.getConstructor(clazz).newInstance(tag).asInstanceOf[T]

  */

  /*
  // how about scala reflect

  val m = ru.runtimeMirror(getClass.getClassLoader)
  val classPerson = ru.typeOf[Post].typeSymbol.asClass
  val cm = m.reflectClass(classPerson)
  val ctor = ru.typeOf[Post].declaration(ru.nme.CONSTRUCTOR).asMethod
  val ctorm = cm.reflectConstructor(ctor)

  def cons = (tag: Tag) => ctorm(tag).asInstanceOf[T]
   */


  // then only ...

  lazy val db = DAO.db
  lazy val query = TableQuery[T](foo)

}

object DAO {
  lazy val db = Database.forConfig("mydb")
}

