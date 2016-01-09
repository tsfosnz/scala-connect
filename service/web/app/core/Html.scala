package core

import play.api.libs.iteratee.{Iteratee, Enumerator}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Html {

  /**
   * Get the Html from the Enumerator
   * Used to get the Result to Html
   *
   * The idea inspired by http://www.ybrikman.com/speaking/
   * https://www.youtube.com/watch?v=4b1XLka0UIw
   *
   * But this code is not relevant to it, but learned by
   * https://www.playframework.com/documentation/2.2.x/Iteratees
   *
   * @param enumerator
   * @return
   */
  def readHtmlBy(enumerator: Enumerator[Array[Byte]]): Future[String] = {
    
    val getHtmlBy: Iteratee[Array[Byte],String] = {
      Iteratee.fold[Array[Byte],String]("") {(result, chunk) => result ++ new String(chunk)}
    }

    enumerator.run[String](getHtmlBy)

  }
  
}
