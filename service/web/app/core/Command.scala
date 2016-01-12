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


  /**
   *
   * Sometimes, you got have a join for M:M relationship,
   * e.g. category : post, this time, you actually want
   * get (category, seq[post..]), or you will have to
   * try make twice db query, groupBy will do this job
   * to avoid second db query
   *
   * @param list
   * @tparam T Entity
   * @tparam B String
   * @return
   */
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

  def groupBy[T, B, A](list: Seq[(T, B, A)]): Map[B, Seq[(T, A)]] = {

    // lession #1,
    // always look up method instead of creating it own

    // changed by suggestion of github@staslev

    // can't avoid to use mutable data, but only accept
    // immutable and output immutable is recommanded
    // https://github.com/scala/scala/blob/v2.11.7/src/library/scala/collection/TraversableLike.scala#L1

    list
      .groupBy({ case (post, category, id) => category })
      .mapValues(_.map({ case (post, category, id) => (post, id) }))


  }



}
