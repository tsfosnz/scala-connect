name := """service"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  //jdbc,
  cache,
  ws,
  specs2 % Test,
  // slick for RDBMS, play 2.x + slick 3.1
  // https://github.com/playframework/play-slick
  "com.typesafe.play" %% "play-slick" % "1.1.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "1.1.1",
  "mysql" % "mysql-connector-java" % "5.1.24",
  "org.reactivemongo" %% "reactivemongo" % "0.11.7",
  "com.h2database" % "h2" % "1.4.187"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// faker
resolvers += "justwrote" at "http://repo.justwrote.it/releases/"
libraryDependencies += "it.justwrote" %% "scala-faker" % "0.3"

// ScalaTest
// https://www.playframework.com/documentation/2.4.x/ScalaTestingWithScalaTest
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalatestplus" %% "play" % "1.4.0-M3" % "test"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
