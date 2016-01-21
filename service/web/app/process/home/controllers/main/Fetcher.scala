package process.home.controllers.main

import javax.inject.Inject

import core._
import models.{TopicEntity, PostEntity}
import play.api.libs.iteratee.Enumerator
import play.api.libs.json.Json
import play.api.mvc._
import service.topic.TopicServ
import service.post.PostServ
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.api.i18n._
import play.api.i18n.I18nSupport

/**
 *
 * @author Tom
 */
// https://www.playframework.com/documentation/2.5.x/ScalaDependencyInjection
class Fetcher @Inject()(val messagesApi: MessagesApi) extends Command with I18nSupport {

  implicit val postReads = Json.reads[PostEntity]
  implicit val postWrites = Json.writes[PostEntity]

  def index = Action.async { request =>

    //implicit val lang = request.acceptLanguages(3)
    implicit val req: RequestHeader = request

    val topics = TopicServ.topics(0, 10)

    val post = this.post(topics)(request).flatMap {
      res => res.header.status == 200 match {
        case true => Html.readHtmlBy(res.body)
        case _ => Future {
          ""
        }
      }
    }

    val head = this.head(topics)(request).flatMap {
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
      Ok.chunked(Enumerator(views.html.home.main.index("", h, p, r)))
    }

  }

  /**
   * Get the post template
   *
   * @return
   */
  protected def post(topics: Future[Seq[TopicEntity]]) = Action.async { request =>


    /*
    val list = for {item <- topics} yield PostServ.getPostsByTopics(Map(
      "item" -> item,
      "postCount" -> 5))

    // here is bug, the data is

    list match {
      case null => Future {
        InternalServerError(fail(ServErrorConst.SystemError))
      }
      case _ =>
        list.flatMap {
          item => item.map { r => Ok(views.html.home.main.list(groupBy(r))) }
        }.recover {
          case err: Throwable =>
            InternalServerError(fail(ServErrorConst.SystemError))
        }
    }
    */

    val page = request.getQueryString("page").getOrElse("1").toInt
    val count = request.getQueryString("count").getOrElse("20").toInt

    val list = PostServ.getPosts((page - 1) * count, count)

    list match {
      case null => Future {
        InternalServerError(fail(ServErrorConst.SystemError))
      }
      case _ =>
        list.map {
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

                Ok(views.html.home.main.list_summary(Map(
                  "data" -> data,
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
  protected def head(topics: Future[Seq[TopicEntity]]) = Action.async { request =>

    topics match {
      case null => Future {
        InternalServerError(fail(ServErrorConst.SystemError))
      }
      case _ =>
        topics.map { c => Ok(views.html._layout.head(c)) }
          .recover {
            case err: Exception =>
              InternalServerError(fail(ServErrorConst.SystemError))
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
