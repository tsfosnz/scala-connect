package model

import org.junit.runner._
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.PlaySpec
import org.specs2.runner._
import service.label.{LabelIo, LabelPostIo}
import service.member.MemberIo
import service.post.PostIo
import service.team._

import scala.concurrent.Await
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
class DbSeedSpec extends PlaySpec with ScalaFutures {

  "Project<Model>" must {

    "populate data in table<project>" in {

      // create all

      Await.result(LabelIo.initialize, Duration("5 seconds"))
      Await.result(LabelPostIo.initialize(true), Duration("5 seconds"))
      Await.result(MemberIo.initialize, Duration("5 seconds"))
      Await.result(PostIo.initialize, Duration("5 seconds"))
      Await.result(TeamIo.initialize, Duration("5 seconds"))
      Await.result(TeamMemberIo.initialize, Duration("5 seconds"))


    }

    "continue..." in {

      println("To be continuted...")

      //Project.remove

    }
  }

}
