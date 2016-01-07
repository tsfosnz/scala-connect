package model

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException
import org.junit.runner._
import org.scalatestplus.play.PlaySpec
import org.specs2.runner._
import service.label.{LabelPostIo, LabelIo}

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
class LabelPostSpec extends PlaySpec {

  "Label<Model>" must {

    "populate data in table<label>" in {


      try {
        Await.result(LabelPostIo.initialize(true), Duration("5 seconds"))
      }

      catch {
        case err: MySQLSyntaxErrorException =>
          Await.result(LabelPostIo.initialize(), Duration("5 seconds"))
      }

      for (j <- 1 to 200) {

        Await.result(LabelPostIo.populate(j % 10 + 1, j), Duration("1 seconds"))

      }
    }

    "continue..." in {

      println("To be continuted...")

      //Project.remove

    }
  }

}
