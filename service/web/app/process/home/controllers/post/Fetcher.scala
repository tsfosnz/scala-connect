package process.home.controllers.post

import core._
import models.PostEntity
import play.api.libs.iteratee.Enumerator
import play.api.libs.json.Json
import play.api.mvc._
import service.post.PostServ

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Fetcher extends Command {

  implicit val postReads = Json.reads[PostEntity]
  implicit val postWrites = Json.writes[PostEntity]

  def index = Action.async { request =>


    val post = this.post(request).flatMap {t => Html.readHtmlBy(t.body)}
    val head = this.head(request).flatMap {t => Html.readHtmlBy(t.body)}
    val right = this.right(request).flatMap {t => Html.readHtmlBy(t.body)}


    for {

      l <- post
      h <- head
      r <- right

    } yield {

      Ok.chunked(Enumerator(views.html.home.index("", h, l, r)))

    }

  }

  /**
   * Get the post list with list view
   *
   * @return
   */
  protected def post = Action.async { request =>

    val post = PostServ.all(0, 200)

    //Thread.sleep(10000)

    post.map {

      r => Ok(views.html.home.block.list(r))

    }


  }

  /**
   * Get the header data with header view
   *
   * @return
   */
  protected def head = Action.async { request =>

    //Thread.sleep(10000)

    //val b = Promise.timeout(Some("Hello....".getBytes), 500)

    Future {
      //Thread.sleep(10000)
      Ok(views.html.home.block.head())
    }

  }

  /**
   * Get the data with right sidebar
   *
   * @return
   */
  protected def right = Action.async { request =>

    Future {
      Ok(views.html.home.block.right(""))
    }

  }

}
