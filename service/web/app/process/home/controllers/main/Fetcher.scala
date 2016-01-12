package process.home.controllers.main

import core._
import models.PostEntity
import play.api.libs.concurrent.Promise
import play.api.libs.iteratee.Enumerator
import play.api.libs.json.Json
import play.api.mvc._
import service.category.CategoryServ
import service.post.PostServ
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.Duration

class Fetcher extends Command {

  implicit val postReads = Json.reads[PostEntity]
  implicit val postWrites = Json.writes[PostEntity]

  def index = Action.async { request =>


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

      Ok.chunked(Enumerator(views.html.home.index("", h, p, r)))

    }

  }

  /**
   * Get the post list with list view
   *
   * @return
   */
  protected def post = Action.async { request =>

    val list = PostServ.getGroupBy(3, 0, 200)


    list.flatMap {

      item => item.map {r => Ok(views.html.home.block.list(groupBy[PostEntity, String, Int](r)))}

    }


  }


  /**
   * Get the header data with header view
   *
   * @return
   */
  protected def head = Action.async { request =>


    val category = CategoryServ.all(0, 10)

    category match {

      case null => Future {
        InternalServerError(fail(ServErrorConst.SystemError))
      }

      case _ =>

        category.map {

          c => Ok(views.html.home.block.head(c))

        }.recover {

          case err: Throwable => InternalServerError(fail(ServErrorConst.SystemError))

        }
    }


  }

  /**
   * Get the data with right sidebar
   *
   * @return
   */
  protected def right = Action.async { request =>

    Future {
      Ok(views.html.home.block.right())
    }

  }

}
