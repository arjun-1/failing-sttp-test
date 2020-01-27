
import zio.test.Assertion._
import zio.test.DefaultRunnableSpec
import zio._
import zio.test._

import sttp.client._
import sttp.client.testing.SttpBackendStub
import sttp.client.impl.zio.TaskMonadAsyncError
import sttp.model.StatusCode


object ClientMocks {

  def createEnv(backend: SttpBackend[Task, Nothing, NothingT]) =
    new ClientLive {
      val httpClient = backend
    }

  val sttpBackendStub = Managed.makeEffect(
    SttpBackendStub[Task, Nothing](TaskMonadAsyncError)
  )(_.close)


  val notFound = 
    sttpBackendStub
      // The following will fail the test
      .map(_.whenAnyRequest.thenRespondNotFound())
      // The following will succeed the test
      // .map(_.whenAnyRequest.thenRespond(
      //   Response(
      //     Left(HttpError("Not found")),
      //     StatusCode.NotFound
      //   )
      // ))
      .map(createEnv)
  
}

object ClientSpec
    extends DefaultRunnableSpec(
      suite("A client")(
        testM("propagates http errors")(
          assertM(
            Client.>.get().run,
            fails(anything)
          ).provideManaged(ClientMocks.notFound)
        )
      )
    )
