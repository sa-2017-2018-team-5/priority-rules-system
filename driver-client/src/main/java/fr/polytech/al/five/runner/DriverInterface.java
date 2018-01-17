package fr.polytech.al.five.runner;

import asg.cliche.Command;
import org.apache.log4j.Logger;
import stubs.route.*;

import java.util.Optional;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class DriverInterface {

    private static final Logger LOGGER = Logger.getLogger(DriverInterface.class);

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
    private RouteWebService routeWebService;

    public DriverInterface(RouteWebService routeWebService) {
        car = new Car();
        initialize();

        this.routeWebService = routeWebService;
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
        Position position = new Position();
        position.setLongitude(longitude);
        position.setLatitude(latitude);

        car.setCurrentPosition(position);
    }

    @Command(name = "set-type")
    public void setType(String type) {
        CarType carType = new CarType();
        carType.setName(type);

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
        Position to = new Position();
        to.setLongitude(longitude);
        to.setLatitude(latitude);

        try {
            Route route = routeWebService.getRoute(car, to);
            LOGGER.info("Route ID: " + route.getId());
            LOGGER.info("Instructions:");
            route.getInstructions().forEach(instruction ->
                    LOGGER.info("* " + instruction));
        } catch (NotAuthorizedCar_Exception e) {
            LOGGER.error("Error while querying the route.");
        }
    }
}
