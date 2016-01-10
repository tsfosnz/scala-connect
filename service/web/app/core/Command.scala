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
   * @tparam T
   * @tparam B
   * @return
   */
  def groupBy[T, B](list: Seq[(T, B)]): Seq[(B, Seq[T])] = {


    // don't use var, but its internal usage
    // might be fine here
    // this method will be quick...
    // it doesn't query db twice

    var category = Set[B]()
    val ret = for {

      t <- list

    } yield {

        !category.contains(t._2) match {

          case true => {
            category += t._2
            //println(category)
            (t._2, for {n <- list.filter(_._2 == t._2)} yield (n._1))
          }

          case _ => null
        }


      }


    ret.filter(_ != null)

  }



}
