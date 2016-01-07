package core

import play.api.libs.json.Json
import play.api.mvc.Controller


class Command extends Controller {

  case class RetVal(error: Boolean, message: String)

  implicit val retValReades = Json.reads[RetVal]
  implicit val retValWrites = Json.writes[RetVal]

  def fail(message: String*) = {

    Json.toJson(RetVal(true, message.mkString("")))

  }

  /**
   * Actually message wasn't optional
   *
   * @param message
   * @return
   */
  def success(message: String*) = {

    Json.toJson(RetVal(false, message.mkString("")))

  }


}
