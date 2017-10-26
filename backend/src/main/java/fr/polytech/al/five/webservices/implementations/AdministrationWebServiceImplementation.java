package fr.polytech.al.five.webservices.implementations;

import fr.polytech.al.five.PriorityReader;
import fr.polytech.al.five.PriorityRegisterer;
import fr.polytech.al.five.entities.CarType;
import fr.polytech.al.five.exceptions.AlreadyExistingCarTypeException;
import fr.polytech.al.five.exceptions.NotExistingCarTypeException;
import fr.polytech.al.five.webservices.AdministrationWebService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.Optional;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@WebService(targetNamespace = "http://www.polytech.fr/al/five/administration")
@Stateless(name = "AdministrationWS")
public class AdministrationWebServiceImplementation
        implements AdministrationWebService {

    @EJB
    private PriorityRegisterer priorityRegister;

    @EJB
    private PriorityReader priorityReader;

    @Override
    public void registerPriority(CarType carType) throws AlreadyExistingCarTypeException {
        priorityRegister.registerPriority(carType);
    }

    @Override
    public void modifyPriority(CarType carType) throws NotExistingCarTypeException {
        priorityRegister.modifyPriority(carType, carType.getPriority());
    }

    @Override
    public CarType findPriorityByName(String typeName) throws NotExistingCarTypeException {
        Optional<CarType> optional = priorityReader.getPriority(typeName);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new NotExistingCarTypeException();
        }
    }
}
