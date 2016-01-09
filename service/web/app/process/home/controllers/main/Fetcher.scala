package process.home.controllers.main

import core._
import models.PostEntity
import play.api.libs.json.Json
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global


class Fetcher extends Controller {

  implicit val postReads = Json.reads[PostEntity]
  implicit val postWrites = Json.writes[PostEntity]

  def index = Action.async { request =>


    //val result = PostServ.all(0, 20)


    val k = new PostFetcher().index(request)

    val m = k.flatMap {

      tt => Html.readHtmlBy(tt.body)

    }

    m.map {

      ttt => {
        println(ttt)
        Ok(views.html.home.index(ttt))
      }
    }


  }

}
