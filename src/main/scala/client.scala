
import sttp.client._
import sttp.client.circe._
import zio._
import io.circe.Error


trait Client {
  val client: Client.Service
}

object Client {
  trait Service {
    def get(): Task[Int]
  }

  object > {
    def get() =
      ZIO.accessM[Client](_.client.get())
  }
}

trait ClientLive extends Client {
  implicit val httpClient: SttpBackend[Task, Nothing, NothingT]

  type SttpRequest[A] =
    RequestT[Identity, Either[ResponseError[Error], A], Nothing]

  def execute[A](request: SttpRequest[A]) =
    request.send.flatMap(response =>
      response.body match {
        case Left(HttpError(_)) =>
          Task.fail(new RuntimeException("bad"))
        case Left(DeserializationError(_, _)) =>
          Task.fail(new RuntimeException("bad"))
        case Right(value) => Task.succeed(value)
      }
    )

  val client = new Client.Service {
    def get() = execute(
      basicRequest
        .get(uri"http://localhost")
        .response(asJson[Int])
    )
  }
}
