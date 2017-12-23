lazy val projects: Seq[ProjectReference] = Seq(arith)

lazy val commonSettings = Seq(
  version := "0.1",
  scalaVersion := "2.12.4",
  scalacOptions ++= Seq("-unchecked", "-deprecation")
)

lazy val root = Project(
  id = "TAPL",
  base = file(".")
).aggregate(projects: _*)
 .settings(name := "TAPL")
 .settings(commonSettings)

lazy val arith = module("arith")
  .settings(name := "arith")
  .settings(commonSettings)
  .settings(Dependencies.test)

def module(name: String): Project = Project(id = name, base = file(name))
