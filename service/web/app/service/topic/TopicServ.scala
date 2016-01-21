package service.topic

import models._
import slick.driver.MySQLDriver.api._

//import scala.concurrent.ExecutionContext.Implicits.global

object TopicServ {

  val topic = TopicQuery
  val db = topic.db

  def queryTopics = for {c <- topic.query} yield c

  def topics(page: Int, count: Int) = {

    try {

      val q = queryTopics.drop(page).take(count)
      val sql = q.result.statements.head

      //println(sql)

      db.run(q.result)

    }

    catch {
      case err: Exception => null
    }

    finally {
      //topic.db.source.close();
    }

  }

}
