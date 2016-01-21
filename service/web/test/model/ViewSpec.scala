package model

import core.ViewHelper
import org.junit.runner._
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.PlaySpec
import org.specs2.runner._
import service.post.PostServ
import slick.driver.MySQLDriver.api._

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
class ViewSpec extends PlaySpec with ScalaFutures {

  "Project<Model>" must {

    "populate data in table<project>" in {

      for (i <- 0 to 100)
      {
        val p = ViewHelper.paging(i, 20, 20)
        println(p)
      }
    }

    "continue..." in {

    }
  }

}
