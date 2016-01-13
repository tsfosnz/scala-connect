package service.label

import core.Service
import models.{LabelItem, TopicItem}
import slick.driver.MySQLDriver.api._


object LabelItemServ extends Service[LabelItem](
  "mydb",
  (tag: Tag) => new LabelItem(tag)) {

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
