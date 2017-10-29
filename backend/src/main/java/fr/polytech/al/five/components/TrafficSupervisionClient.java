package fr.polytech.al.five.components;

import fr.polytech.al.five.TrafficSupervisor;
import fr.polytech.al.five.entities.TrafficInformation;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;
import java.net.ConnectException;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@Stateless
public class TrafficSupervisionClient implements TrafficSupervisor {

    private final static Logger LOGGER = Logger.getLogger(TrafficSupervisionClient.class);

    @Override
    public TrafficInformation getTraffic(String roadId) {
        int port = 9090;
        String host = "localhost";
        String serviceName = "/traffic-api/road/section=";

        WebClient webClient = WebClient.create("http://" + host + ":" + port + serviceName + roadId)
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .query("section", "request");

        try {
            JSONObject jsonResult = new JSONObject(webClient.get(String.class));

            return TrafficInformation.valueOf(
                    jsonResult.getString("status").toUpperCase());
        } catch (Exception e){
            LOGGER.error("Could not reach the external service.");
            return TrafficInformation.UNKNOWN;
        }
    }
}
