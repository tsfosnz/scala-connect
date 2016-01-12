package core

import play.api.libs.json.Json
import play.api.mvc.Controller


trait Command extends Controller {

  case class RetVal(error: Boolean, message: String)

  implicit val retValReades = Json.reads[RetVal]
  implicit val retValWrites = Json.writes[RetVal]

  def fail(message: String*) = {

    Json.toJson(RetVal(true, message.mkString("")))

  }

  /**
   * Actually message wasn't optional
   *
   * @param message
   * @return
   */
  def success(message: String*) = {

    Json.toJson(RetVal(false, message.mkString("")))

  }


  /*
  def groupBy[T, B](list: Seq[(T, B)]): Map[B, Seq[T]] = {

    // lession #1,
    // always look up method instead of creating it own

    // changed by suggestion of github@staslev

    // can't avoid to use mutable data, but only accept
    // immutable and output immutable is recommanded
    // https://github.com/scala/scala/blob/v2.11.7/src/library/scala/collection/TraversableLike.scala#L1

    list
      .groupBy({ case (post, category) => category })
      .mapValues(_.map({ case (post, category) => post }))


  }
  */

  def groupBy[T, B, Int](list: Seq[(T, B, Int)]): Seq[((B, Int), Seq[T])] = {


    // changed by suggestion of github@staslev

    // can't avoid to use mutable data, but only accept
    // immutable and output immutable is recommanded
    // https://github.com/scala/scala/blob/v2.11.7/src/library/scala/collection/TraversableLike.scala#L1

    val result = list
      .groupBy({ case (post, category, id) => (category, id) })
      .mapValues(_.map({ case (post, category, id) => post }))
      .toSeq
      .sortWith((a, b) => a._1._2.toString.toInt < b._1._2.toString.toInt)

    result
  }



}
