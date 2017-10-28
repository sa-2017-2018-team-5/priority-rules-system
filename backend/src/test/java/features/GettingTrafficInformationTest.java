package features;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import fr.polytech.al.five.components.TrafficSupervisionClient;
import fr.polytech.al.five.entities.TrafficInformation;

import static org.junit.Assert.assertEquals;

public class GettingTrafficInformationTest {

    private TrafficSupervisionClient trafficSupervisionClient = new TrafficSupervisionClient();
    private TrafficInformation trafficInformation;


    @Given("^a road section with id (.*)$")
    public void getRoadInfo(String road_id) {
        trafficInformation = trafficSupervisionClient.getTraffic(road_id);
    }

    @Then("^the road status should be (.*)$")
    public void checkRoadInfo(String status) {
        assertEquals(trafficInformation, TrafficInformation.fromName(status));
    }

}
