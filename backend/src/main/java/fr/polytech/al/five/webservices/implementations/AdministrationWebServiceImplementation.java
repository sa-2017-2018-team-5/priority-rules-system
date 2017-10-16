package fr.polytech.al.five.webservices.implementations;

import fr.polytech.al.five.entities.CarType;
import fr.polytech.al.five.webservices.AdministrationWebService;
import org.apache.commons.lang.NotImplementedException;

import javax.ejb.Stateless;
import javax.jws.WebService;

@WebService
@Stateless(name = "AdministrationWS")
public class AdministrationWebServiceImplementation
        implements AdministrationWebService {

    @Override
    public void registerPriority(CarType carType) {
        System.out.println("ADD NEW CAR TYPE : " + carType.getName());
    }

    @Override
    public void modifyPriority(CarType carType) {
        throw new NotImplementedException();
    }
}
