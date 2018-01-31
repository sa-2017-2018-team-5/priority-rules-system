package fr.polytech.al.five.priorities.services;

import fr.polytech.al.five.priorities.business.CarType;
import fr.polytech.al.five.priorities.databases.PriorityDatabase;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@Path("/priorities")
@Produces(MediaType.APPLICATION_JSON)
public class PrioritiesWebService {

    private static final Logger LOGGER = Logger.getLogger(PrioritiesWebService.class);

    @EJB
    private PriorityDatabase database;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(final CarType newType) {
        System.out.println("Trying to register car type '" + newType.getName() + "'.");

        if (database.register(newType)) {
            LOGGER.info("Succeeded to register car type '" + newType.getName() + "'.");
            return Response.status(Response.Status.CREATED).build();
        } else {
            LOGGER.info("Failed to register car type '" + newType.getName() + "'.");
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @GET
    @Path("/{type_name}")
    public Response read(@PathParam("type_name") String typeName) {
        Optional<CarType> carType = database.find(typeName);

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
        List<CarType> carTypes = database.findAll();
        CarType[] typesArray = carTypes.toArray(new CarType[carTypes.size()]);

        return Response.status(Response.Status.OK)
                .entity(typesArray)
                .build();
    }
}
