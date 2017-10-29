package fr.polytech.al.five.components;

import fr.polytech.al.five.TrafficSupervisor;
import fr.polytech.al.five.entities.TrafficInformation;
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

    private final static Logger LOGGER = Logger.getLogger(TrafficSupervisionClient.class);
    private static String TRAFFIC_API_HOST = getEnvVariable("traffic_api_host", "localhost");
    private static int TRAFFIC_API_PORT = Integer.parseInt(getEnvVariable("traffic_api_port", "9191"));

    private static String getEnvVariable(String variableName, String defaultValue) {
        if (System.getenv().get(variableName) == null) {
            return defaultValue;
        }

        return System.getenv().get(variableName);
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
            LOGGER.error("Could not reach the external service. At adress:" +
                    "http://" + host + ":" + port + serviceName + roadId);
            return TrafficInformation.UNKNOWN;
        }
    }
}
