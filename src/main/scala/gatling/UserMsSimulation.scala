import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class UserMsSimulation extends Simulation {

    val httpProtocol = http
        .baseUrl("http://localhost:8182")
        .inferHtmlResources(BlackList(""".*\.css""", """.*\.js""", """.*\.ico"""), WhiteList())
        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("it-IT,it;q=0.8,en-US;q=0.5,en;q=0.3")
        .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0")

    val scn = scenario("Users Scenario")
        .exec(http("Get Users")
            .get("/user"))

    setUp(scn.inject(
        nothingFor(5 seconds),
        atOnceUsers(1000),
        rampUsers(500) during (10 seconds),
        constantUsersPerSec(500) during (20 seconds),
        rampUsersPerSec(100) to 750 during (2 minutes) randomized
    )).protocols(httpProtocol)

}