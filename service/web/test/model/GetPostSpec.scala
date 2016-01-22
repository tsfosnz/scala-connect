package model

import models.PostEntity
import org.junit.runner._
import org.scalatest.FlatSpec
import org.scalatest.concurrent.{ScalaFutures, Futures}
import org.scalatest.time.{Seconds, Span}
import org.scalatestplus.play.PlaySpec
import org.specs2.runner._
import migration._
import service.post.PostServ
import service.topic.TopicServ

import scala.concurrent.duration.Duration
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import slick.driver.MySQLDriver.api._

/**
 * The test is flexible, and we can use different test framework,
 * and one task, is to test the model
 * to put all models in one test file is better idea, so we can
 * see all of them at same place
 *
 * Usually when we build the modal, we will do CRUD, so thats the
 * test target: which is CRUD
 *
 * This is more like a UnitTest, not a TDD
 */
@RunWith(classOf[JUnitRunner])
class GetPostSpec extends PlaySpec with ScalaFutures {

  "PostServ.getPost" must {

    "get a post by id" in {

      for (i <- 1 to 1) {

        val post = PostServ.getPost(i)

        println(post)

        Await.result(
          post.map {
            item =>
              val head = item.head
              println(Map(
              "post" -> head._1._1,
              "topic" -> head._1._2,
              "member" ->(head._2, head._3)
            ))
          }.recover {
            case err =>
              println(err)
              println(err.getMessage)
          },
          Duration("5 seconds")
        )

      }

    }

  }

}
