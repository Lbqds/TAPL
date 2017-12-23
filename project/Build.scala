import sbt._
import Keys._

object Dependencies {
  val testVersion = "3.0.4"
  libraryDependencies += "org.scalactic" %% "scalactic" % testVersion
  val scalaTest = "org.scalatest" %% "scalatest" % testVersion % "test"

  val l = libraryDependencies
  val test = l ++= Seq(scalaTest)
}