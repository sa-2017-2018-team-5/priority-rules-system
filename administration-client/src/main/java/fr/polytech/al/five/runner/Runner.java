package fr.polytech.al.five.runner;

import fr.polytech.al.five.remote.ServiceProvider;
import org.apache.log4j.Logger;
import stubs.administration.*;

import static stubs.administration.CarStatus.EMERGENCY;

public class Runner {

    private static Logger LOGGER = Logger.getLogger(Runner.class);

    private static CarType CAR_TYPE;
    private static AdministrationWebService ADMINISTRATION_WS;

    public static void main(String... args) {
        ADMINISTRATION_WS = ServiceProvider.getAdministrationWebService();

        create("FIREFIGHTERS", 100, EMERGENCY);
        create("POLICEMEN", 90, EMERGENCY);

        fetch("FIREFIGHTERS");

        update("FIREFIGHTERS", 120);
        update("POLICEMEN", 110);

        fetch("FIREFIGHTERS");
        fetch("POLICEMEN");
    }

    private static void create(String name, int priority, CarStatus status) {
        LOGGER.info("[x] Creating a new car type.");
        LOGGER.info("[x] * Name: " + name);
        LOGGER.info("[x] * Priority: " + priority);
        LOGGER.info("[x] * Status: " + status);

        CAR_TYPE = new CarType();
        CAR_TYPE.setName(name);
        CAR_TYPE.setPriority(priority);
        CAR_TYPE.setStatus(status);

        try {
            ADMINISTRATION_WS.registerPriority(CAR_TYPE);
            LOGGER.info("[s] Type '" + name + "' registered!");
        } catch (AlreadyExistingCarType_Exception e) {
            LOGGER.error("[e] Could not register the type '" + name + "'.");
        }
    }

    private static void fetch(String name) {
        LOGGER.info("[f] Fetching the car type '" + name + "'.");

        try {
            CAR_TYPE = ADMINISTRATION_WS.findPriorityByName(name);
            LOGGER.info("[f] Type '" + name + "' fetched!");
            LOGGER.info("[f] '" + name + "' ; priority: " + CAR_TYPE.getPriority() + ".");
        } catch (NotExistingCarType_Exception e) {
            LOGGER.error("[e] Could not fetch the car type '" + name + "'.");
        }
    }

    private static void update(String name, int updatedPriority) {
        LOGGER.info("[x] Updating type '" + name + "'.");
        LOGGER.info("[x] * Priority: " + updatedPriority);

        CAR_TYPE.setPriority(updatedPriority);

        try {
            ADMINISTRATION_WS.modifyPriority(CAR_TYPE);
            LOGGER.info("[s] Type '" + name + "' updated!");
        } catch (NotExistingCarType_Exception e) {
            LOGGER.error("[e] Could not update the type '" + name + "'.");
        }
    }
}
