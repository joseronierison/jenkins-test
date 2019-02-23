import akka.event.NoLogging
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.ContentTypes._
import akka.http.scaladsl.model.{HttpResponse, HttpRequest}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.stream.scaladsl.Flow
import org.scalatest._

class ServiceSpec extends FlatSpec with Matchers with ScalatestRouteTest with Service {
  override def testConfigSource = "akka.loglevel = WARNING"
  override def config = testConfig
  override val logger = NoLogging
  val healthCheckReply = HealthStatus("UP");
  val helloWorld = Reply("Hello World");

  "Service" should "respond to health check" in {
    Get(s"/health") ~> routes ~> check {
      status shouldBe OK
      contentType shouldBe `application/json`
      responseAs[HealthStatus] shouldBe healthCheckReply
    }
  }

  "Service" should "respond to" in {
    Get(s"/") ~> routes ~> check {
      status shouldBe OK
      contentType shouldBe `application/json`
      responseAs[Reply] shouldBe helloWorld
    }
  }
}
