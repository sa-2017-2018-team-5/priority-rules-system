package fr.polytech.al.five.webservices.implementations;

import fr.polytech.al.five.PriorityReader;
import fr.polytech.al.five.PriorityRegisterer;
import fr.polytech.al.five.entities.CarType;
import fr.polytech.al.five.exceptions.AlreadyExistingCarType;
import fr.polytech.al.five.exceptions.NotExistingCarType;
import fr.polytech.al.five.webservices.AdministrationWebService;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.List;
import java.util.Optional;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@WebService(targetNamespace = "http://www.polytech.fr/al/five/administration")
@Stateless(name = "AdministrationWS")
public class AdministrationWebServiceImplementation
        implements AdministrationWebService {

    private static final Logger LOGGER = Logger.getLogger(AdministrationWebServiceImplementation.class);

    @EJB
    private PriorityRegisterer priorityRegister;

    @EJB
    private PriorityReader priorityReader;

    @Override
    public void registerPriority(CarType carType) throws AlreadyExistingCarType {
        priorityRegister.registerPriority(carType);
    }

    @Override
    public void modifyPriority(CarType carType) throws NotExistingCarType {
        priorityRegister.modifyPriority(carType, carType.getPriority());
    }

    @Override
    public CarType findPriorityByName(String typeName) throws NotExistingCarType {
        Optional<CarType> optional = priorityReader.getPriority(typeName);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            LOGGER.info("Could not find a car type named '" + typeName + "'.");
            throw new NotExistingCarType();
        }
    }

    @Override
    public List<CarType> findAllPriorities() {
        return priorityReader.getPriorities();
    }
}
