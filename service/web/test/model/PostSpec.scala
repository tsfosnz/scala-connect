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
class PostSpec extends PlaySpec with ScalaFutures {

  "Project<Model>" must {

    "populate data in table<project>" in {

      // how many thread it will use?
      // how about mysql thread?

      // thread pool = 12
      // connection pool = 12
      // it will use 17 mysql thread, and 2 active

      // thread = 2
      // connection pool = 2
      // it will use 7 mysql thread

      for (i <- 1 to 1000) {

        val list = PostServ.getPosts(1, 200)

        list.onSuccess {
          case result => println(i + ":")
        }

        list.onFailure {
          case e => println(e)
        }

        Await.result(Future {list;TopicServ.topics(0, 10)}, Duration(5000, "seconds"))
        //Await.result(TopicServ.topics(0, 10), Duration(5000, "seconds"))
      }

      //while(true){}
    }

    "continue..." in {

      println("To be continuted...")

      //Project.remove

    }
  }

}
