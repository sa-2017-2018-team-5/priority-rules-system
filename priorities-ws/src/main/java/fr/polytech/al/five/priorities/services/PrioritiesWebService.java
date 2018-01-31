package fr.polytech.al.five.priorities.services;

import fr.polytech.al.five.priorities.business.CarStatus;
import fr.polytech.al.five.priorities.business.CarType;
import fr.polytech.al.five.priorities.databases.PriorityDatabase;
import fr.polytech.al.five.priorities.exceptions.AlreadyExistingCarType;
import fr.polytech.al.five.priorities.exceptions.NotExistingCarType;
import org.apache.log4j.Logger;

import javax.jws.WebService;
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(final CarType newType) {
        if (PriorityDatabase.getInstance().register(newType)) {
            System.out.println("REGISTERED");

            return Response.status(Response.Status.CREATED).build();
        } else {
            System.out.println("FAILURE");

            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @GET
    @Path("/{type_name}")
    public Response read(@PathParam("type_name") String typeName) {
        System.out.println(typeName);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    public Response readAll() {
        System.out.println("READ ALL TYPES");

        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
