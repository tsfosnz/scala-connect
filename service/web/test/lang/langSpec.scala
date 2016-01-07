package test.lang

import org.junit.runner._
import org.scalatestplus.play.PlaySpec
import org.specs2.runner._
import play.api.libs.json._

import scala.collection.mutable.ArrayBuffer

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class FilterSpec extends PlaySpec {

  "Filter: Play body" should {

    "get the only input by filter" in {

      val requiredOnly = Set("param3", "param2")

      val data: Map[String, Seq[String]] = Map(
        "param1" -> Seq("what"),
        "param2" -> Seq("which")
      )

      val data2: Option[Map[String, Seq[String]]] = Option(Map(
        "param1" -> Seq("what"),
        "param2" -> Seq("which")
      ))

      val data3: Option[Map[String, Seq[String]]] = None

      val result = data3 match {
        case None => Map()
        case _ => data2.get.filter(
          d => requiredOnly.contains(d._1)
        )
      }

      println(result)


    }

  }
}

@RunWith(classOf[JUnitRunner])
class JsonSpec extends PlaySpec {

  "Json" should {

    "set precise of BigDecimal type" in {


      case class Location(lat: BigDecimal, long: BigDecimal)

      val df = new java.text.DecimalFormat()


      implicit val locationWrites = new Writes[Location] {
        def writes(location: Location) = Json.obj(
          "lat" -> location.lat,
          "long" -> location.long
        )
      }


      //println(Json.toJson(Location(90.00, 37.132)))

      val data:ArrayBuffer[Int] = ArrayBuffer(1, 2, 3)
      val search = 0

      var i = 0
      var v = data(i)
      val length = data.length - 1

      while((v - search) < 0 && i < length) {

        i = i + 1;
        v = data(i)

      }

      if (i == length) {

        data.append(search)

      } else {

        data.insert(i, search)
      }


      println(data.length)
      println(data)





    }

  }
}

