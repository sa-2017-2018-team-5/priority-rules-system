package fr.polytech.al.five.components;

import fr.polytech.al.five.PriorityReader;
import fr.polytech.al.five.PriorityRegisterer;
import fr.polytech.al.five.entities.CarType;
import fr.polytech.al.five.exceptions.AlreadyExistingCarType;
import fr.polytech.al.five.exceptions.NotExistingCarType;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@Stateless
public class PriorityRegister implements PriorityRegisterer, PriorityReader {

    private static Logger LOGGER = Logger.getLogger(PriorityRegister.class);

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
            return Optional.empty();
        }
    }

    @Override
    public List<CarType> getPriorities() {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<CarType> criteria = builder.createQuery(CarType.class);
        Root<CarType> root = criteria.from(CarType.class);

        // Not supposed to be a problem: there should not be a lot of car types.
        criteria.select(root);
        TypedQuery<CarType> query = manager.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public void registerPriority(CarType type) throws AlreadyExistingCarType {
        if (getPriority(type.getName()).isPresent()) {
            throw new AlreadyExistingCarType();
        } else {
            manager.persist(type);
        }
    }

    @Override
    public void modifyPriority(CarType type, Integer priority) throws NotExistingCarType {
        Optional<CarType> optionalCarType = getPriority(type.getName());

        if (optionalCarType.isPresent()) {
            CarType carType = optionalCarType.get();
            carType.setPriority(priority);

            manager.persist(carType);
        } else {
            throw new NotExistingCarType();
        }
    }
}
