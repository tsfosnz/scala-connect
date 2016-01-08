package process.member.controllers.post

import models.PostEntity
import play.api.libs.json.Json
import play.api.mvc._
import service.post.PostServ

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import core._

class Updater extends Command {

  implicit val postReads = Json.reads[PostEntity]
  implicit val postWrites = Json.writes[PostEntity]

  /**
   * Add the new project item to the table
   *
   * @return
   */
  def add = Action.async { request =>

    // it's a filter, to get valid input value
    val only = Set("title", "body")

    val data: Map[String, Seq[String]] =
      request.body.asFormUrlEncoded match {

        case None => Map[String, Seq[String]]()
        case _ => request.body.asFormUrlEncoded.get.filter(
          d => only.contains(d._1)
        )
      }

    // only authorized manager can add project here
    // so the id coming from the authorized manager obj
    // assume its 1, for test only

    val authorId = 1

    //val timeout = play.api.libs.concurrent.Promise.timeout("Oops", 1)

    data.size == 2 match {

      case false =>

        Future {

          NotAcceptable(fail(ReqErrorConst.InvalidInput))

        }

      case _ =>

        val result = PostServ.add(authorId, data)

        result match {
          case null => Future {
            InternalServerError(fail(ServErrorConst.SystemError))
          }
          case _ => result.map(_ => Ok(success())).recover {

            case err =>
              InternalServerError(fail(ServErrorConst.SystemError))
          }
        }

    }
  }

  def update(id: Int) = Action.async { request =>


    // it's a filter, to get valid input value
    val required = Set("name", "description")

    val data: Map[String, Seq[String]] =
      request.body.asFormUrlEncoded match {

        case None => Map[String, Seq[String]]()
        case _ => request.body.asFormUrlEncoded.get.filter(
          d => required.contains(d._1)
        )
      }

    //val timeout = play.api.libs.concurrent.Promise.timeout("Oops", 1)

    data.size == 2 match {

      case false =>

        Future {

          NotAcceptable(fail(ReqErrorConst.InvalidInput))

        }

      case _ =>

        val result = PostServ.update(id, data)

        result match {
          case null => Future {
            InternalServerError(fail(ServErrorConst.SystemError))
          }
          case _ => result.map {

            r => r > 0 match {

              case false => NotFound(fail(ServErrorConst.NotFound))
              case true => Ok(success(ServErrorConst.Success))

            }

          }.recover {

            case err =>
              InternalServerError(fail(ServErrorConst.SystemError))
          }
        }

    }

  }

  def remove(id: Int) = Action.async { request =>

    //val timeout = play.api.libs.concurrent.Promise.timeout("Oops", 1)

    val result = PostServ.remove(id)

    result match {
      case null => Future {
        InternalServerError(fail(ServErrorConst.SystemError))
      }
      case _ => result.map {

        r => r > 0 match {

          case false => NotFound(fail(ServErrorConst.NotFound))
          case true => Ok(success(ServErrorConst.Success))

        }

      }.recover {

        case err =>
          InternalServerError(fail(ServErrorConst.SystemError))
      }
    }


  }
}
