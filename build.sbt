import Dependencies._

ThisBuild / scalaVersion     := "2.13.1"

lazy val root = (project in file("."))
.settings(
  name := "failing-test",
)

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.3")

scalacOptions ++= Seq(
  "-encoding", "utf-8",
  "-Yrangepos",
  "-Ywarn-unused:imports",
  "-Xlint",
  "-Xcheckinit",
  "-unchecked",
  "-deprecation",
  "-explaintypes",
  "-Ywarn-dead-code",
  "-Ywarn-extra-implicit",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused"
)

libraryDependencies ++= deps

testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))
