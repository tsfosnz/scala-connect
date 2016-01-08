package process.home.controllers.main

import core._
import models.PostEntity
import play.api.libs.json.Json
import play.api.mvc._
import service.post.PostServ
import scala.concurrent.ExecutionContext.Implicits.global


class Fetcher extends Command {

  implicit val postReads = Json.reads[PostEntity]
  implicit val postWrites = Json.writes[PostEntity]

  def index = Action.async { request =>

    // get a list of post

    // get a list of tag

    //

    val result = PostServ.all(0, 20)

    for {

      i <- result

    } yield {

      Ok(views.html.home.index("", i))
    }


  }
}
