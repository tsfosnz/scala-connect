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

    val list = PostServ.getAllBy(0, 50)

    for {
      r <- list

    } yield {
      Ok(views.html.home.block.list(groupBy(r)))
    }

  }



  /**
   * Get the header data with header view
   *
   * @return
   */
  protected def head = Action.async { request =>

    //Thread.sleep(10000)

    val category = CategoryServ.all(0, 10)

    category.map {

      c => Ok(views.html.home.block.head(c))

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
