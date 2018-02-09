package fr.polytech.al.five.routes.components.implementations;

import fr.polytech.al.five.routes.business.CarStatus;
import fr.polytech.al.five.routes.business.CarType;
import fr.polytech.al.five.routes.components.PriorityChecker;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.ejb.Stateless;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Stateless
public class PrioritiesServiceClient implements PriorityChecker {

    private static final Logger LOGGER = Logger.getLogger(PrioritiesServiceClient.class);
    private static final String PRIORITIES_SERVICE_HOST = getEnvVariable("PRIORITIES_SERVICE_HOST", "localhost");
    private static final int PRIORITIES_SERVICE_PORT = Integer.parseInt(getEnvVariable("PRIORITIES_SERVICE_PORT", "8080"));

    private static String getEnvVariable(String variableName, String defaultValue) {
        String environmentVariable = System.getenv(variableName);
        if (environmentVariable == null) {
            environmentVariable = defaultValue;
        }

        return environmentVariable;
    }

    @Override
    public Optional<CarType> checkPriority(String priorityName) {
        String address = "http://" + PRIORITIES_SERVICE_HOST + ":" + PRIORITIES_SERVICE_PORT + "/prs-priorities/priorities/" + priorityName;

        WebClient webClient = WebClient.create(address).accept(MediaType.APPLICATION_JSON);

        try {
            JSONObject jsonResult = new JSONObject(webClient.get(String.class));

            CarType carType = new CarType(priorityName);
            carType.setPriority(jsonResult.getInt("priority"));
            carType.setStatus(CarStatus.valueOf(jsonResult.getString("status")));

            return Optional.of(carType);
        } catch (Exception e){
            LOGGER.error("Could not reach the external service. At address:" + address);
            return Optional.empty();
        }
    }
}