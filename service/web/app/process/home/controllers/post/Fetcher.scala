package process.home.controllers.post

import core._
import models.PostEntity
import play.api.libs.iteratee.Enumerator
import play.api.libs.json.Json
import play.api.mvc._
import service.post.Post

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class Fetcher extends Command {

  implicit val postReads = Json.reads[PostEntity]
  implicit val postWrites = Json.writes[PostEntity]


  def all(count: Int, page: Option[Int]) = Action.async { request =>

    val p =
      if (page.getOrElse(0) - 1 < 0)
        0
      else
        page.get - 1

    val result: Future[Seq[PostEntity]] = Post.all(p, count)
    //val timeout = play.api.libs.concurrent.Promise.timeout("Oops", 1)

    val error = scala.concurrent.Future {
      "System error"
    }

    result match {
      case null => Future {
        InternalServerError(fail(ServErrorConst.SystemError))
      }
      case _ =>

        // what if it didn't success

        result.map {
          data => //Ok(Json.toJson(data))
            Result(
              header = ResponseHeader(200, Map(CONTENT_TYPE -> "application/json")),
              body = Enumerator(Json.toJson(data).toString().getBytes)
            )
        }

    }

  }

  def one(id: Int) = Action.async { request =>

    val result = Post.one(id)

    val error = scala.concurrent.Future {
      "System error"
    }

    result match {
      case null => Future {
        InternalServerError(fail(ServErrorConst.SystemError))
      }
      case _ =>

        // what if it didn't success

        result.map {
          data => //Ok(Json.toJson(data))
            Result(
              header = ResponseHeader(200, Map(CONTENT_TYPE -> "application/json")),
              body = Enumerator(Json.toJson(data).toString().getBytes)
            )
        }

    }

  }
}