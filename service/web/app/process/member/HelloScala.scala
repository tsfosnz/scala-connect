package process.member

import play.api.libs.iteratee.Enumerator
import play.api.mvc._

// https://playframework.com/documentation/2.4.x/ScalaHome
class HelloScala extends Controller {

  def index = Action {


    Ok("hello world")


  }

  def more (name: String) = Action {


    Result(

      header = ResponseHeader(400, Map(CONTENT_TYPE -> "text/plain")),
      body = Enumerator(("Hello world!" + name).getBytes())

    )
  }
}
