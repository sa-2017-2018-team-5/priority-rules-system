package utils;

import stubs.administration.AdministrationWebService;
import stubs.administration.AlreadyExistingCarType_Exception;
import stubs.administration.CarType;
import stubs.administration.NotExistingCarType_Exception;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class FalseAdministrationWebService implements AdministrationWebService {
    private int findPriorityByNameCount = 0;
    private int registerPriorityCount = 0;
    private int findAllPrioritiesCount = 0;
    private int modifyPriorityCount = 0;
    private int notExistingCarCount = 0;
    private int alreadyExistingCarCount = 0;


    private List<CarType> types = new ArrayList<>();

    @Override
    public CarType findPriorityByName(String arg0) throws NotExistingCarType_Exception {
        findPriorityByNameCount++;

        for (CarType type : types) {
            if (arg0.equals(type.getName())) {
                return type;
            }
        }

        notExistingCarCount++;
        throw new NotExistingCarType_Exception();
    }

    @Override
    public void registerPriority(CarType arg0) throws AlreadyExistingCarType_Exception {
        registerPriorityCount++;

        for (CarType type : types) {
            if (arg0.getName().equals(type.getName())) {
                alreadyExistingCarCount++;
                throw new AlreadyExistingCarType_Exception();
            }
        }

        types.add(arg0);
    }

    @Override
    public List<CarType> findAllPriorities() {
        findAllPrioritiesCount++;

        return types;
    }

    @Override
    public void modifyPriority(CarType arg0) throws NotExistingCarType_Exception {
        modifyPriorityCount++;

        for (CarType type : types) {
            if (arg0.getName().equals(type.getName())) {
                type.setPriority(arg0.getPriority());
                return;
            }
        }

        notExistingCarCount++;
        throw new NotExistingCarType_Exception();
    }

    public int getFindPriorityByNameCount() {
        return findPriorityByNameCount;
    }

    public int getRegisterPriorityCount() {
        return registerPriorityCount;
    }

    public int getFindAllPrioritiesCount() {
        return findAllPrioritiesCount;
    }

    public int getModifyPriorityCount() {
        return modifyPriorityCount;
    }

    public int getNotExistingCarCount() {
        return notExistingCarCount;
    }

    public int getAlreadyExistingCarCount() {
        return alreadyExistingCarCount;
    }
}
