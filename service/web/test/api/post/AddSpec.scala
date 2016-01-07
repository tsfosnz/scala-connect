package api.post

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.test.Helpers._
import play.api.test.{FakeRequest, WithApplication}

@RunWith(classOf[JUnitRunner])
class AddSpec extends Specification {

  "Post.API.add" should {

    "/posts/new, should add value when correct" in new WithApplication{


      val api = "/post/new"

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
      //println(contentAsString(home))
    }

    "/posts/new, should not add value when empty" in new WithApplication{


      val api = "/post/new"

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
      //println(contentAsString(home))
    }

    "/posts/new, should filter mal-format value" in new WithApplication{


      val api = "/post/new"

      // P200
      // Programming in scala

      val data = Array (
        ("name", "hello"),
        ("description", "test one"),
        ("a", "b")
      )

      val home = route (

        FakeRequest(POST, api).withFormUrlEncodedBody(data: _*)

      ).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "application/json")
      //contentAsString(home) must contain ("Your new application is ready.")
      //println(contentAsString(home))
    }

  }
}

