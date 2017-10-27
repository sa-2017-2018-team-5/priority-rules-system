package fr.polytech.al.five.commands;

import asg.cliche.Command;
import org.apache.log4j.Logger;
import stubs.administration.*;

import java.util.List;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class AdministrationCommands {

    private static Logger LOGGER = Logger.getLogger(AdministrationCommands.class);

    private static AdministrationWebService ADMINISTRATION_WS;

    public AdministrationCommands(AdministrationWebService administrationWebService) {
        ADMINISTRATION_WS = administrationWebService;
    }

    @Command(name = "list-all")
    public void listAll() {
        List<CarType> carTypes = ADMINISTRATION_WS.findAllPriorities();

        String headers = String.format("%-15s %-10s %-10s",
                "Name", "Priority", "Status");
        String separator = headers.replaceAll(".", "―");

        LOGGER.info(headers);
        LOGGER.info(separator);

        carTypes.forEach(carType ->
                LOGGER.info(String.format("%-15s %-10s %-10s",
                        carType.getName(),
                        carType.getPriority(),
                        carType.getStatus().toString())));
    }

    @Command
    public void create(String name, int priority, String carStatus) {
        CarStatus status = CarStatus.valueOf(carStatus);

        LOGGER.info(String.format("Creating { name: %s; priority: %d; status: %s }.",
                name, priority, carStatus));

        CarType carType = new CarType();
        carType.setName(name);
        carType.setPriority(priority);
        carType.setStatus(status);

        try {
            ADMINISTRATION_WS.registerPriority(carType);
            LOGGER.info("Registration of car type '" + name + "' succeed!");
        } catch (AlreadyExistingCarType_Exception e) {
            LOGGER.error("Registration of car type '" + name + "' failed!");
        }
    }

    @Command
    public void fetch(String name) {
        try {
            CarType carType = ADMINISTRATION_WS.findPriorityByName(name);
            LOGGER.info(String.format("Fetched { name: %s; priority: %d; status: %s }.",
                    carType.getName(), carType.getPriority(), carType.getStatus()));
        } catch (NotExistingCarType_Exception e) {
            LOGGER.error("Could not fetch the car type '" + name + "'.");
        }
    }

    @Command
    public void update(String name, int updatedPriority) {
        CarType carType = new CarType();
        carType.setName(name);
        carType.setPriority(updatedPriority);

        try {
            ADMINISTRATION_WS.modifyPriority(carType);
            LOGGER.info("Type '" + name + "' updated!");
        } catch (NotExistingCarType_Exception e) {
            LOGGER.error("Could not update the type '" + name + "'.");
        }
    }
}
