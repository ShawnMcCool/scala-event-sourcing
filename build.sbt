name := "Scala Event Sourcing"

version := "0.1"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10" % "2.0" % Test,
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % Test
)