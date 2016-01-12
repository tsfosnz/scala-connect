package process.home.controllers

import play.api.libs.iteratee.Enumerator
import play.api.mvc._

// https://playframework.com/documentation/2.4.x/ScalaHome
class Application extends Controller {

   def index = Action {


     Ok("Welcome")


   }

 }
