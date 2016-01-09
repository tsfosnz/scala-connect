package migration

import org.junit.runner._
import org.scalatestplus.play.PlaySpec
import org.specs2.runner._

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
class DbSeedSpec extends PlaySpec {

  "Db seed" must {

    "populate data to table for test" in {

      // create all

      for (i <- 0 to 9) {
        Await.result(CategoryTable.populate, Duration("5 seconds"))
      }

      for (i <- 0 to 99) {
        Await.result(PostTable.populate, Duration("5 seconds"))
      }

      for (i <- 0 to 9) {
        Await.result(MemberTable.populate, Duration("5 seconds"))
      }

      // Await.result(LabelPostTable.populate, Duration("5 seconds"))


    }

    "continue" in {

      // println("To be continuted...")

      //Project.remove

    }
  }

}
