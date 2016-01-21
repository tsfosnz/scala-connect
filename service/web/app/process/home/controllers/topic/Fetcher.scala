package process.home.controllers.topic

import javax.inject.Inject

import core._
import models.{TopicEntity, PostEntity}
import play.api.i18n.{I18nSupport, _}
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
class Fetcher @Inject()(val messagesApi: MessagesApi) extends Command with I18nSupport {

  implicit val postReads = Json.reads[PostEntity]
  implicit val postWrites = Json.writes[PostEntity]

  def index(id: Int) = Action.async { request =>

    //implicit val lang = request.acceptLanguages(3)
    implicit val req: RequestHeader = request


    val post = this.post(id)(request).flatMap {

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
      //println(p)

      Ok.chunked(Enumerator(views.html.home.topic.index("", h, p, r)))

    }

  }

  /**
   * Get the post template
   *
   * @return
   */
  protected def post(id: Int) = Action.async { request =>


    val page = request.getQueryString("page").getOrElse("1").toInt
    val count = request.getQueryString("count").getOrElse("20").toInt

    val list = PostServ.getPostsByTopic(Map(
      "topicId" -> id,
      "page" -> (page - 1) * count,
      "count" -> count
    ))

    // here is bug, the data is

    list match {

      case null => Future {
        InternalServerError(fail(ServErrorConst.SystemError))
      }

      case _ => list.map {

        post =>
          val total: Int = post("total").asInstanceOf[Int]

          total == 0 match {
            case true => Ok("")
            case _ =>
              // not eval, when total == 0
              lazy val data = post("data").asInstanceOf[Seq[(PostEntity, TopicEntity)]]
              lazy val last = Math.ceil(total / count).toInt
              lazy val prev = Math.max(page - 1, 1)
              lazy val next = Math.min(page + 1, last)

              lazy val pagination = ViewHelper.paging(page, count, total)

              println(pagination)

              Ok(views.html.home.topic.list(Map(
                "data" -> data.map {item => item._1},
                "name" -> data.head._2.name,
                "total" -> total,
                "page" -> page,
                "count" -> count,
                "prev" -> prev,
                "next" -> next,
                "last" -> last,
                "pagination" -> pagination
              )))
          }
      }.recover {
        case err: Exception =>
          println(err)
          println(err.getMessage)
          InternalServerError(fail(ServErrorConst.SystemError))
      }

    }
  }


  /**
   * Get the head template
   *
   * @return
   */
  protected def head = Action.async { request =>

    val category = TopicServ.topics(0, 10)

    category match {

      case null => Future {
        InternalServerError(fail(ServErrorConst.SystemError))
      }

      case _ => category.map {
        c => Ok(views.html._layout.head(c))
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
      Ok(views.html.home.topic.right())
    }

  }

}
