package fr.polytech.al.five.runner;

import asg.cliche.Command;
import fr.polytech.al.five.business.Car;
import fr.polytech.al.five.business.CarType;
import fr.polytech.al.five.business.Position;
import org.apache.log4j.Logger;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class DriverInterface {

    private static final Logger LOGGER = Logger.getLogger(DriverInterface.class);

    private WebTarget resource;

    private static Optional<Integer> getIntegerEnv(String reference) {
        String variable = System.getenv(reference);

        if (variable != null) {
            try {
                return Optional.of(Integer.parseInt(variable));
            } catch (Exception e) {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    private static Optional<Float> getFloatEnv(String reference) {
        String variable = System.getenv(reference);

        if (variable != null) {
            try {
                return Optional.of(Float.parseFloat(variable));
            } catch (Exception e) {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    private static Optional<String> getStringEnv(String reference) {
        return Optional.ofNullable(System.getenv(reference));
    }

    private Car car;

    public DriverInterface(String url) {
        car = new Car();
        initialize();

        this.resource = ClientBuilder.newClient().target(url);
    }

    private void initialize() {
        getStringEnv("DEFAULT_CAR_TYPE").ifPresent(this::setType);
        getIntegerEnv("DEFAULT_CAR_ID").ifPresent(this::setId);

        Optional<Float> longitude = getFloatEnv("DEFAULT_CAR_LONGITUDE");
        Optional<Float> latitude = getFloatEnv("DEFAULT_CAR_LATITUDE");
        if (longitude.isPresent() && latitude.isPresent()) {
            setPosition(longitude.get(), latitude.get());
        }
    }

    @Command(name = "set-id")
    public void setId(int id) {
        car.setId(id);
    }

    @Command(name = "set-position")
    public void setPosition(float longitude, float latitude) {
        Position position = new Position(longitude, latitude);

        car.setCurrentPosition(position);
    }

    @Command(name = "set-type")
    public void setType(String type) {
        CarType carType = new CarType(type);
        car.setType(carType);
    }

    @Command(name = "display-car")
    public void displayCar() {
        LOGGER.info(String.format("Car: { id: %d, position: (%f, %f), type: %s }",
                car.getId(),
                car.getCurrentPosition().getLongitude(),
                car.getCurrentPosition().getLatitude(),
                car.getType().getName()));
    }

    @Command(name = "route-to")
    public void fetchRoute(float longitude, float latitude) {

        Response response = this.resource.request(MediaType.APPLICATION_JSON).post(Entity.entity("" +
                        "{\"action\":\"ASK\",\"content\":{\"car\":{\"id\": " + this.car.getId() + ",\"type\":\"" + this.car.getType() + "\"," +
                        "\"currentPosition\":{\"longitude\":" + this.car.getCurrentPosition().getLongitude() + ",\"latitude\":" + this.car.getCurrentPosition().getLatitude() + "}}," +
                        "\"destination\":{\"longitude\":" + longitude + ",\"latitude\":" + latitude + "}}}",
                MediaType.APPLICATION_JSON_TYPE));

        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
            LOGGER.info("Success! " + response.getStatus());
            LOGGER.info(response.readEntity(String.class));
        } else {
            LOGGER.info("ERROR! " + response.getStatus());
            LOGGER.info(response.getEntity());
        }
    }
}
