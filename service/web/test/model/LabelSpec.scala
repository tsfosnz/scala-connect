package model

import org.junit.runner._
import org.scalatestplus.play.PlaySpec
import org.specs2.runner._
import service.label.Label
import service.member.Member

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
class LabelSpec extends PlaySpec {

  "Label<Model>" must {

    "populate data in table<label>" in {

      Label.initialize

      for (i <- 0 to 10) {
        Label.populate
      }
    }

    "continue..." in {

      println("To be continuted...")

      //Project.remove

    }
  }

}
