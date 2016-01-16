package service.topic

import models._
import slick.driver.MySQLDriver.api._


object TopicItemServ {


  val topicItem = TopicItemQuery

  def test = {

    //println(t)


    //db

    val q = topicItem.query.drop(1).take(20)
    val r = topicItem.db.run(q.result)

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
