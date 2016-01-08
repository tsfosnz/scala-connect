package service.label

import java.text.SimpleDateFormat

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException
import core.Service
import models.LabelPost
import slick.ast.Node
import slick.driver.MySQLDriver.api._
import slick.lifted.{AbstractTable, BaseTag}
import slick.model.Table

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Abcd {

  object LabelPostServ extends Service[LabelPost]("mydb", abc) {

    //val _query = TableQuery[LabelPost]


    def test = {

      //println(t)


      //db

      val q = query.drop(1).take(20)
      val r = db.run(q.result)

      println(q.result.statements.head)
      println(r)

      r

    }


  }

  //private val ab: LabelPost = null
  private def abc(tag: Tag) = new LabelPost(tag)

}
/*

class Test extends Abcd {

  def k = {

    LabelPostServ.test

  }

}*/
