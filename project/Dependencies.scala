import sbt._

object Dependencies {
  object Versions {
    val http4s = "0.21.0-M6"
    val zio = "1.0.0-RC17"
    val sttp = "2.0.0-RC5"
  }

  val zio = "dev.zio" %% "zio" % Versions.zio
  val sttp = "com.softwaremill.sttp.client" %% "core" % Versions.sttp
  val sttpCirce = "com.softwaremill.sttp.client" %% "circe" % Versions.sttp
  val zioTest = "dev.zio" %% "zio-test" % Versions.zio
  val zioTestSbt = "dev.zio" %% "zio-test-sbt" % Versions.zio
  val sttpZio = "com.softwaremill.sttp.client" %% "async-http-client-backend-zio" % Versions.sttp


  val deps = Seq(
    zioTest % Test,
    zioTestSbt % Test,
    zio,
    sttp,
    sttpCirce,
    sttpZio
  )
}