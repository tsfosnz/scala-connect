package process.home.controllers.main

import play.api.mvc.Action
import service.post.PostServ
import core._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PostFetcher extends Command {

  def index = Action.async { request =>

    // get a list of post

    // get a list of tag

    //

    Future {
      Ok(views.html.home.test("Hello world"))
    }


  }
}
