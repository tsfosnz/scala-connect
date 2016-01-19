package service.topic

import java.text.SimpleDateFormat

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException
import core._
import models._
import slick.driver.MySQLDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global

object TopicServ {

  val topic = TopicQuery

  def queryTopics = for {c <- topic.query} yield c

  def topics(page: Int, count: Int) = {

    try {

      val q = queryTopics.drop(page).take(count)
      val sql = q.result.statements.head

      println(sql)
      
      topic.db.run(q.result)

    }

    catch {
      case err: Exception => null
    }

  }

}
