package process.home.controllers.team

import core._
import models.{MemberEntity, TeamEntity, TeamFullEntity}
import play.api.libs.json.Json
import play.api.mvc._
import service.team.TeamIo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Fetcher extends Command {

   implicit val teamReads = Json.reads[TeamEntity]
   implicit val teamWrites = Json.writes[TeamEntity]

   implicit val memberReads = Json.reads[MemberEntity]
   implicit val memberWrites = Json.writes[MemberEntity]

   implicit val teamfullRead = Json.reads[TeamFullEntity]
   implicit val teamfullWrite = Json.writes[TeamFullEntity]


   def all(count: Int, page: Option[Int]) = Action.async { request =>

     val p =
       if (page.getOrElse(0) - 1 < 0)
         0
       else
         page.get - 1

     val error = scala.concurrent.Future {
       "System error"
     }

     val result = TeamIo.all(p, count)

     result match {
       case null => Future.firstCompletedOf(Seq(error)).map {
         case msg: String => InternalServerError(fail(msg))
       }
       case _ => {

         result.flatMap {

           k => k.map {
             n => Ok(Json.prettyPrint(Json.toJson(n)))
           }
         }
       }
     }
   }

   def one(id: Int) = Action.async { request =>

     val result: Future[Seq[TeamEntity]] = TeamIo.one(id)

     result.map(
       result =>
         Ok(Json.toJson(result))
     )

   }
 }
