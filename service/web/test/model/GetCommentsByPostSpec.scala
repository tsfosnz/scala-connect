package model

import models.CommentEntity
import org.junit.runner._
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.PlaySpec
import org.specs2.runner._
import service.post.PostServ

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration.Duration

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
class GetCommentsByPostSpec extends PlaySpec with ScalaFutures {

  "PostServ.getPost" must {

    "get a post by id" in {

      for (i <- 1 to 1) {

        val post = PostServ.getCommentsByPost(i, 0, 20)

        println(post)

        Await.result(
          post.map {
            item =>
              println(item("comment").toString)

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
