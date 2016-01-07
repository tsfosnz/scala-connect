package test.model

import java.util.concurrent.Executors

import org.junit.runner._
import org.scalatest.FlatSpec
import org.scalatestplus.play.PlaySpec
import org.specs2.runner._
import play.api.mvc.{ResponseHeader, Result}
import service.team.TeamIo
import org.scalatest.concurrent.ScalaFutures._


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
class TeamSpec extends PlaySpec {

  "<Model>" must {

    "populate data in table<project>" in {

      for (i <- 0 to 100) {
        TeamIo.populate
      }
    }

    "continue..." in {

      println("To be continuted...")

      //.remove

    }
  }

}

@RunWith(classOf[JUnitRunner])
class TeamMemberSpec extends FlatSpec {

  it should "add random member to team" in {

  }

}
