package fr.polytech.al.five.runner;

import asg.cliche.Command;
import org.apache.log4j.Logger;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class AdministrationInterface {

    private static final Logger LOGGER = Logger.getLogger(AdministrationInterface.class);

    private WebTarget resource;

    public AdministrationInterface(String url) {
        this.resource = ClientBuilder.newClient().target(url);
    }

    @Command(name = "all-types")
    public void listAll() {
        Response response = this.resource.request(MediaType.APPLICATION_JSON).get();

        treatResponse(response);
    }

    @Command
    public void create(String name, int priority, String carStatus) {
        Response response = this.resource.request(MediaType.APPLICATION_JSON).post(Entity.entity("" +
                        "{\"name\": \"" + name + "\",\"priority\": "+priority + ",\"status\": \""+carStatus+"\"}",
                MediaType.APPLICATION_JSON_TYPE));

        treatResponse(response);
    }

    private void treatResponse(Response response) {
        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
            LOGGER.info("Success! " + response.getStatus());
            LOGGER.info(response.readEntity(String.class));
        } else {
            LOGGER.info("ERROR! " + response.getStatus());
            LOGGER.info(response.getEntity());
        }
    }

    /*@Command
    public void fetch(String name) {
        try {
            CarType carType = administrationWebService.findPriorityByName(name);
            LOGGER.info(String.format("Fetched { name: %s; priority: %d; status: %s }.",
                    carType.getName(), carType.getPriority(), carType.getStatus()));
        } catch (NotExistingCarType_Exception e) {
            LOGGER.error("Could not fetch the car type '" + name + "'.");
        }
    }

    /*@Command
    public void update(String name, int updatedPriority) {
        CarType carType = new CarType();
        carType.setName(name);
        carType.setPriority(updatedPriority);

        try {
            administrationWebService.modifyPriority(carType);
            LOGGER.info("Type '" + name + "' updated!");
        } catch (NotExistingCarType_Exception e) {
            LOGGER.error("Could not update the type '" + name + "'.");
        }
    }*/
}
