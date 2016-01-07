package process.home.controllers.test

import core._
import models.{MemberEntity, TeamEntity, TeamFullEntity}
import play.api.libs.iteratee.{Enumeratee, Enumerator}
import play.api.libs.json.Json
import play.api.mvc._
import play.twirl.api.Html
import service.post.PostIo
import service.team.TeamIo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class Fetcher extends Command {

   // the ugly json, this is the simple way to use json
   // for different type in play 2.x.x
   implicit val teamReads = Json.reads[TeamEntity]
   implicit val teamWrites = Json.writes[TeamEntity]

   implicit val memberReads = Json.reads[MemberEntity]
   implicit val memberWrites = Json.writes[MemberEntity]

   implicit val teamfullRead = Json.reads[TeamFullEntity]
   implicit val teamfullWrite = Json.writes[TeamFullEntity]


   def team = Action.async { request =>

     TeamIo.test

     /*
     val team = Team.all(0, 10)
     val member = Member.all(0, 200)

     val f = for {

       m <- member
       t <- team

     } yield {

         // m is the result of future <member>
         // so its Seq[MemberEntity]...

         m.map(

           // item is MemberEntity

           item =>
             item.id.get % 3 match {

               case 0 => (t(0).id.get, item.id.get)
               case 1 => (t(1).id.get, item.id.get)
               case 2 => (t(2).id.get, item.id.get)

             })
       }

     f.map(
       data => {


         data.map(

           d => TeamMember.add(d._1, d._2)

         )

         Ok {

           "Hello!!"
         }
       }
     )
     */

     Future {
       Ok("")
     }

   }


   def project = Action { request =>

     // HTTP chunked transfer

     /*
     Ok.chunked(Enumerator(
       """<script>console.log('kiki')</script>""",
       """<script>console.log('foo')</script>""",
       """<script>console.log('bar')</script>""").andThen(Enumerator.eof)).as(HTML)
     */

     val t = PostIo.test

     println(t)

     val toCometMessage = Enumeratee.map[String] {

       data => Html("""<script>console.log('""" + data + """')</script>""")

     }

     val events = Enumerator("Hey", "how", "are", "you")
     //Ok.chunked(events &> toCometMessage)


     Result(
       header = ResponseHeader(200, Map(CONTENT_TYPE -> "application/json")),
       body = Enumerator("".toString().getBytes)
     )

   }

 }
