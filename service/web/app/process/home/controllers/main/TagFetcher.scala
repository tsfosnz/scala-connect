package process.home.controllers.main

import core.Command
import play.api.mvc.Action
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class TagFetcher extends Command {

  def index = Action.async { request =>
    
    Future {
      Ok("hello tag!!")
    }


  }
  
}
