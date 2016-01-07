package api.post

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test.{FakeRequest, WithApplication}

@RunWith(classOf[JUnitRunner])
class RemoveSpec extends Specification {

  "Project.API.remove" should {

    "/projects/remove, should remove value when correct" in new WithApplication{


      val api = "/project/1/remove"

      // P200
      // Programming in scala

      val home = route (

        FakeRequest(DELETE, api)

      ).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "application/json")
      //contentAsString(home) must contain ("Your new application is ready.")
      println(contentAsString(home))
    }

    "/projects/remove, should not remove when not exist" in new WithApplication{


      val api = "/project/1000/remove"

      val home = route (

        FakeRequest(DELETE, api)

      ).get

      status(home) must equalTo(NOT_FOUND)
      contentType(home) must beSome.which(_ == "application/json")
      //contentAsString(home) must contain ("Your new application is ready.")
      contentAsJson(home) must equalTo(Json.toJson(Map("error" -> true)))

      //println(contentAsString(home))
    }


  }
}

