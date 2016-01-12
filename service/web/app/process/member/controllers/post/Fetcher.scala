package process.member.controllers.post

import models.PostEntity
import play.api.libs.iteratee.{Enumeratee, Enumerator}
import play.api.libs.json.Json
import play.api.mvc._
import play.twirl.api.Html
import service.post.PostServ

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import core._


class Fetcher extends Command {

  implicit val postReads = Json.reads[PostEntity]
  implicit val postWrites = Json.writes[PostEntity]


  def all(count: Int, page: Option[Int]) = Action.async { request =>

    val p =
      if (page.getOrElse(0) - 1 < 0)
        0
      else
        page.get - 1

    val result = PostServ.getAllBy(p, count).map {

      item => item.map {

        r => r._1
      }

    }
    //val timeout = play.api.libs.concurrent.Promise.timeout("Oops", 1)

    val error = scala.concurrent.Future {
      "System error"
    }

    result match {
      case null => Future {
        InternalServerError(fail(ServErrorConst.SystemError))
      }
      case _ =>

        result.map {
          data => //Ok(Json.toJson(data))
            Result(
              header = ResponseHeader(200, Map(CONTENT_TYPE -> "application/json")),
              body = Enumerator(Json.toJson(data).toString().getBytes)
            )
        }.recover {

          case err =>
            InternalServerError(fail(ServErrorConst.SystemError))
        }

    }

  }

  def one(id: Int) = Action.async { request =>

    val result = PostServ.one(id)

    val error = scala.concurrent.Future {
      "System error"
    }

    result match {
      case null => Future {
        InternalServerError(fail(ServErrorConst.SystemError))
      }
      case _ =>

        result.map {
          data => //Ok(Json.toJson(data))
            Result(
              header = ResponseHeader(200, Map(CONTENT_TYPE -> "application/json")),
              body = Enumerator(Json.toJson(data).toString().getBytes)
            )
        }.recover {

          case err =>
            InternalServerError(fail(ServErrorConst.SystemError))
        }

    }

  }
}
