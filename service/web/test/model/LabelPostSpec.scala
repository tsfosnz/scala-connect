package model

import org.junit.runner._
import org.scalatestplus.play.PlaySpec
import org.specs2.runner._
import service.label.{LabelPost, Label}

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
class LabelPostSpec extends PlaySpec {

  "Label<Model>" must {

    "populate data in table<label>" in {

      LabelPost.initialize

      for (j <- 1 to 200) {

        LabelPost.populate(j % 10 + 1, j)

      }
    }

    "continue..." in {

      println("To be continuted...")

      //Project.remove

    }
  }

}