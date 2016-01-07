package api.post

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.libs.json.{Json, JsValue}
import play.api.test.Helpers._
import play.api.test.{FakeRequest, WithApplication}

@RunWith(classOf[JUnitRunner])
class UpdateSpec extends Specification {

  "Post.API.update" should {

    "/posts/update, should add value when correct" in new WithApplication{


      val api = "/post/1/update"

      // P200
      // Programming in scala

      val data = Array (
        ("name", "hello"),
        ("description", "test one")
      )

      val home = route (

        FakeRequest(POST, api).withFormUrlEncodedBody(data: _*)

      ).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "application/json")
      //contentAsString(home) must contain ("Your new application is ready.")
      println(contentAsString(home))
    }

    "/posts/update, should not add value when empty" in new WithApplication{


      val api = "/post/1/update"

      // P200
      // Programming in scala

      val data = Array (
        //("name", "hello"),
        //("description", "test one"),
        ("a", "b")
      )

      val home = route (

        FakeRequest(POST, api).withFormUrlEncodedBody(data: _*)

      ).get

      status(home) must equalTo(NOT_FOUND)
      contentType(home) must beSome.which(_ == "application/json")
      //contentAsString(home) must contain ("Your new application is ready.")
      contentAsJson(home) must equalTo(Json.toJson(Map("error" -> true)))

      //println(contentAsString(home))
    }

    "/posts/update, should not found" in new WithApplication{


      val api = "/post/1000/update"

      // P200
      // Programming in scala

      val data = Array (
        ("name", "hello"),
        ("description", "hey"),
        ("a", "b")
      )

      val home = route (

        FakeRequest(POST, api).withFormUrlEncodedBody(data: _*)

      ).get

      status(home) must equalTo(NOT_FOUND)
      contentType(home) must beSome.which(_ == "application/json")
      //contentAsString(home) must contain ("Your new application is ready.")
      contentAsJson(home) must equalTo(Json.toJson(Map("error" -> true)))
    }

  }
}

