package fr.polytech.al.five.routes.components.implementations;

import fr.polytech.al.five.routes.business.TrafficInformation;
import fr.polytech.al.five.routes.components.TrafficSupervisor;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@Stateless
public class TrafficSupervisionClient implements TrafficSupervisor {

    private static final Logger LOGGER = Logger.getLogger(TrafficSupervisionClient.class);
    private static final String TRAFFIC_API_HOST = getEnvVariable("TRAFFIC_CONDITIONS_HOST", "localhost");
    private static final int TRAFFIC_API_PORT = Integer.parseInt(getEnvVariable("TRAFFIC_CONDITIONS_PORT", "9191"));

    private static String getEnvVariable(String variableName, String defaultValue) {
        String environmentVariable = System.getenv(variableName);
        if (environmentVariable == null) {
            environmentVariable = defaultValue;
        }

        return environmentVariable;
    }

    @Override
    public TrafficInformation getTraffic(String roadId) {
        int port = TRAFFIC_API_PORT;
        String host = TRAFFIC_API_HOST;
        String serviceName = "/traffic-api/road";

        WebClient webClient = WebClient.create("http://" + host + ":" + port + serviceName)
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .query("section", roadId);

        try {
            JSONObject jsonResult = new JSONObject(webClient.get(String.class));

            return TrafficInformation.valueOf(
                    jsonResult.getString("status").toUpperCase());
        } catch (Exception e){
            LOGGER.error("Could not reach the external service. At address:" +
                    "http://" + host + ":" + port + serviceName + roadId);
            return TrafficInformation.UNKNOWN;
        }
    }
}
