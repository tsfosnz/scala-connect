package process.home.controllers.main

import javax.inject.Inject

import core._
import models.PostEntity
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.iteratee.Enumerator
import play.api.libs.json.Json
import play.api.mvc._
import service.post.PostServ
import service.topic.TopicServ

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 *
 * @author Tom
 */
// https://www.playframework.com/documentation/2.5.x/ScalaDependencyInjection
class Test@Inject()(val messagesApi: MessagesApi) extends Command with I18nSupport {

  implicit val postReads = Json.reads[PostEntity]
  implicit val postWrites = Json.writes[PostEntity]

  def index = Action.async { request =>


    // here this.post(request) still a Future?
    // then what happened?
    // Action is a Future always..

    val post = this.post(request).flatMap {

      res => res.header.status == 200 match {

        case true => Html.readHtmlBy(res.body)
        case _ => Future {
          ""
        }
      }

    }

    val head = this.head(request).flatMap {

      res => res.header.status == 200 match {

        case true => Html.readHtmlBy(res.body)
        case _ => Future {
          ""
        }
      }
    }

    val right = this.right(request).flatMap {

      res => res.header.status == 200 match {

        case true => Html.readHtmlBy(res.body)
        case _ => Future {
          ""
        }
      }

    }


    for {

      h <- head
      p <- post
      r <- right

    } yield {

      //println(h)

      Ok.chunked(Enumerator(views.html.home.main.index("", h, p, r)))

    }

  }

  /**
   * Get the post template
   *
   * @return
   */
  protected def post = Action { request =>

    val list = PostServ.getAllBy(0, 200)

    Ok("")

  }


  /**
   * Get the head template
   *
   * @return
   */
  protected def head = Action.async { request =>


    val category = TopicServ.getAllBy(0, 10)

    category match {

      case null => Future {
        InternalServerError(fail(ServErrorConst.SystemError))
      }

      case _ =>

        category.map {

          c => Ok(views.html.home.main.head(c))

        }.recover {

          case err: Throwable => InternalServerError(fail(ServErrorConst.SystemError))

        }
    }


  }

  /**
   * Get the right template
   *
   * @return
   */
  protected def right = Action.async { request =>

    Future {
      Ok(views.html.home.main.right())
    }

  }

}
