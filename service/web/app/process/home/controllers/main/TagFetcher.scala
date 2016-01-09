package process.home.controllers.main

import core.Command
import play.api.mvc.Action
import service.post.PostServ
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class TagFetcher extends Command {

  def index = Action.async { request =>

    // get a list of post

    // get a list of tag

    //

    Future {
      Ok("hello tag!!")
    }


  }
  
}
