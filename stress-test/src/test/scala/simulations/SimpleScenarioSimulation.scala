package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps

class SimpleScenarioSimulation extends Simulation{

  val http_priorities =
    http
      .baseURL("http://localhost:8080/prs-priorities/")
      .acceptHeader("application/json")
      .header("Content-Type", "application/json")

  val http_routes =
    http
      .baseURL("http://localhost:8081/prs-routes/")
      .acceptHeader("application/json")
      .header("Content-Type", "application/json")

  val http_tl =
    http
      .baseURL("http://localhost:8082/prs-traffic-lights/")
      .acceptHeader("application/json")
      .header("Content-Type", "application/json")
  /**
    * Scenario to register the different priorities
    */
  val createRegisterPriorities =
    scenario("Create different vehicle priority")
      .repeat(1){
    exec(
      http("Create FIREFIGHTERS of type EMERGENCY")
        .post("priorities/")
        .body(
          StringBody("""{"name": "FIREFIGHTERS", "priority": 100, "status": "EMERGENCY"}""")
        )
        .check(status.is(201))
        .check(bodyString.saveAs("priority-firefighter"))
    )
    .exec(
      http("Create GREEN_CARS of type PRIVILEGED")
        .post("priorities/")
        .body(
          StringBody("""{"name": "GREEN_CARS", "priority": 20, "status": "PRIVILEGED"}""")

        )
        .check(status.is(201))
        .check(bodyString.saveAs("priority-green-cars"))
    )
    .exec(
      http("Create CARPOOLING of type PRIVILEGED")
        .post("priorities/")
        .body(
          StringBody("""{"name": "CARPOOLING", "priority": 10, "status": "PRIVILEGED"}""")

        )
        .check(status.is(201))
        .check(bodyString.saveAs("priority-car-pooling"))
    )
  }

  /**
    * Simulate a client demanding a route
    */
  val demandNewRoute =
    scenario("Demand new route")
    .repeat(10){
      exec(
        http("FIREFIGHTERS demand route")
          .post("routes/")
          .body(
            StringBody("""{"action": "ASK","content": {"car": {"id": 1, "type": "FIREFIGHTERS","currentPosition": {"longitude": 12,"latitude": 13}},"destination": {"longitude": 1,"latitude": 1}}}""")
          )
          .check(status.is(200))
          .check(bodyString.saveAs("route-request-firefighter"))
      )

        .exec(
        http("GREEN_CARS demand route")
          .post("routes/")
          .body(
            StringBody("""{"action": "ASK","content": {"car": {"id": 2, "type": "GREEN_CARS","currentPosition": {"longitude": 12,"latitude": 13}},"destination": {"longitude": 1,"latitude": 1}}}""")
          )
          .check(status.is(200))
          .check(bodyString.saveAs("route-request-green-cars"))
      )

        .exec(
        http("CARPOOLING demand route")
          .post("routes/")
          .body(
            StringBody("""{"action": "ASK","content": {"car": {"id": 3, "type": "CARPOOLING","currentPosition": {"longitude": 12,"latitude": 13}},"destination": {"longitude": 1,"latitude": 1}}}""")
          )
          .check(status.is(200))
          .check(bodyString.saveAs("route-request-car-pooling"))
      )
  }

  /**
    * Simulate traffic light car detection
    */
  val makeWayForVehicle =
    scenario("Make way for vehicle")
    .repeat(10){
      exec(
        http("car-seen FIREFIGHTERS")
          .post("observations/")
          .body(
            StringBody("""{"content": {"status": "SEEN", "car": 1, "trafficLight": 5}}""")
          )
          .check(status.is(200))
          .check(bodyString.saveAs("car-seen-firefighters"))
      )
        .exec(
        http("car-seen GREEN_CARS")
          .post("observations/")
          .body(
            StringBody("""{"content": {"status": "SEEN", "car": 2, "trafficLight": 3}}""")
          )
          .check(status.is(200))
          .check(bodyString.saveAs("car-seen-green-cars"))
      )
        .exec(
        http("car-seen CARPOOLING")
          .post("observations/")
          .body(
            StringBody("""{"content": {"status": "SEEN", "car": 3, "trafficLight": 4}}""")
          )
          .check(status.is(200))
          .check(bodyString.saveAs("car-seen-car-pooling"))
      )
        .exec(
        http("car-passed FIREFIGHTERS")
          .post("observations/")
          .body(
            StringBody("""{"content": {"status": "PASSED", "car": 1, "trafficLight": 5}}""")
          )
          .check(status.is(200))
          .check(bodyString.saveAs("car-seen-firefighters"))
      )
        .exec(
        http("car-passed GREEN_CARS")
          .post("observations/")
          .body(
            StringBody("""{"content": {"status": "PASSED", "car": 2, "trafficLight": 3}}""")
          )
          .check(status.is(200))
          .check(bodyString.saveAs("car-seen-green-cars"))
      )
        .exec(
        http("car-passed CARPOOLING")
          .post("observations/")
          .body(
            StringBody("""{"content": {"status": "PASSED", "car": 3, "trafficLight": 4}}""")
          )
          .check(status.is(200))
          .check(bodyString.saveAs("car-seen-car-pooling"))
      )
    }
  setUp(createRegisterPriorities.inject(rampUsers(1) over (30 seconds)).protocols(http_priorities),
    demandNewRoute.inject(rampUsers(50) over (30 seconds)).protocols(http_routes),
    makeWayForVehicle.inject(rampUsers(50) over (30 seconds)).protocols(http_tl))

}