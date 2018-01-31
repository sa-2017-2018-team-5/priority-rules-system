package fr.polytech.al.five.priorities.services;

import fr.polytech.al.five.priorities.business.CarType;
import fr.polytech.al.five.priorities.databases.PriorityDatabase;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@Path("/priorities")
@Produces(MediaType.APPLICATION_JSON)
public class PrioritiesWebService {

    private static final PriorityDatabase DATABASE = PriorityDatabase.getInstance();
    private static final Logger LOGGER = Logger.getLogger(PrioritiesWebService.class);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(final CarType newType) {
        LOGGER.info("Trying to register car type '" + newType.name + "'.");

        if (PriorityDatabase.getInstance().register(newType)) {
            LOGGER.info("Succeeded to register car type '" + newType.name + "'.");
            return Response.status(Response.Status.CREATED).build();
        } else {
            LOGGER.info("Failed to register car type '" + newType.name + "'.");
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @GET
    @Path("/{type_name}")
    public Response read(@PathParam("type_name") String typeName) {
        Optional<CarType> carType = DATABASE.find(typeName);

        if (carType.isPresent()) {
            return Response
                    .status(Response.Status.OK)
                    .entity(carType.get())
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    public Response readAll() {
        return Response.status(Response.Status.OK)
                .entity(DATABASE.findAll().toArray())
                .build();
    }
}
