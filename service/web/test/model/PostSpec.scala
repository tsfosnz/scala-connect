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

      val list = PostServ.all(0, 20)

      val group = (list: Seq[(PostEntity, String)]) => {


        // Map[String -> Seq[PostEntity]]

        val category = Set[String]()

        val map = for {

          t <- list

        } yield {

            !category.contains(t._2) match {

              case true => {
                category + t._2
                (t._2, for {n <- list.filter(_._2 === t._2)} yield (t._1))
              }

              case _ => null
            }

          }

      }


    }

    "continue..." in {

      println("To be continuted...")

      //Project.remove

    }
  }

}
