package api.post

import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._


/**
 * All test will be here, basically /route: CRUD..
 */
@RunWith(classOf[JUnitRunner])
class AllSpec extends Specification {

  "Post.API.all" should {

    "/posts/count, should return json" in new WithApplication {


      val api = "/posts/20"

      val home = route(

        FakeRequest(GET, api)

      ).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "application/json")
      //contentAsString(home) must contain ("Your new application is ready.")
      //println(contentAsString(home))
      println(Json.prettyPrint(contentAsJson(home)))
    }
  }
}
