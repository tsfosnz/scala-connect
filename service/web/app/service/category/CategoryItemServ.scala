package service.category

import core.{Service}
import models.CategoryItem
import slick.driver.MySQLDriver.api._


object CategoryItemServ extends Service[CategoryItem](
  "mydb",
  (tag: Tag) => new CategoryItem(tag)) {

  def test = {

    //println(t)


    //db

    val q = query.drop(1).take(20)
    val r = db.run(q.result)

    println(q.result.statements.head)
    println(r)

    r

  }



}

/*

class Test extends Abcd {

  def k = {

    LabelPostServ.test

  }

}*/
