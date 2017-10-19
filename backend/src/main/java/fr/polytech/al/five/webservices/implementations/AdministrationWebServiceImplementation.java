package fr.polytech.al.five.webservices.implementations;

import fr.polytech.al.five.PriorityRegisterer;
import fr.polytech.al.five.entities.CarType;
import fr.polytech.al.five.webservices.AdministrationWebService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

@WebService(targetNamespace = "http://www.polytech.fr/al/five/administration")
@Stateless(name = "AdministrationWS")
public class AdministrationWebServiceImplementation
        implements AdministrationWebService {

    @EJB private PriorityRegisterer priorityRegister;

    @Override
    public void registerPriority(CarType carType) {
        priorityRegister.registerPriority(carType);
    }

    @Override
    public void modifyPriority(CarType carType) {
        priorityRegister.modifyPriority(carType, carType.getPriority());
    }
}
