package service.topic

import core.{Service}
import models.TopicItem
import slick.driver.MySQLDriver.api._


object TopicItemServ extends Service[TopicItem](
  "mydb",
  (tag: Tag) => new TopicItem(tag)) {

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
