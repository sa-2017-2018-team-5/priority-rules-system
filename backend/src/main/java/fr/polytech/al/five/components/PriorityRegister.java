package fr.polytech.al.five.components;

import fr.polytech.al.five.PriorityReader;
import fr.polytech.al.five.PriorityRegisterer;
import fr.polytech.al.five.entities.CarType;
import fr.polytech.al.five.exceptions.AlreadyExistingCarTypeException;
import fr.polytech.al.five.exceptions.NotExistingCarTypeException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@Stateless
public class PriorityRegister implements PriorityRegisterer, PriorityReader {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Optional<CarType> getPriority(String typeName) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<CarType> criteria = builder.createQuery(CarType.class);
        Root<CarType> root = criteria.from(CarType.class);

        criteria.select(root).where(builder.equal(root.get("name"), typeName));
        TypedQuery<CarType> query = manager.createQuery(criteria);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            // TODO Log something.
            return Optional.empty();
        }
    }

    @Override
    public void registerPriority(CarType type) throws AlreadyExistingCarTypeException {
        if (getPriority(type.getName()).isPresent()) {
            throw new AlreadyExistingCarTypeException();
        } else {
            manager.persist(type);
        }
    }

    @Override
    public void modifyPriority(CarType type, Integer priority) throws NotExistingCarTypeException {
        Optional<CarType> optionalCarType = getPriority(type.getName());

        if (optionalCarType.isPresent()) {
            CarType carType = optionalCarType.get();
            carType.setPriority(priority);

            manager.persist(carType);
        } else {
            throw new NotExistingCarTypeException();
        }
    }
}
