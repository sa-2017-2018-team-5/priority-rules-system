package fr.polytech.al.five.priorities.databases;

import fr.polytech.al.five.priorities.business.CarType;

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

@Stateless
public class PriorityDatabase {

    @PersistenceContext
    private EntityManager manager;

    public boolean register(CarType newType) {
        if (find(newType.getName()).isPresent()) {
            return false;
        } else {
            manager.persist(newType);
            return true;
        }
    }

    public Optional<CarType> find(String typeName) {
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

    public List<CarType> findAll() {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<CarType> criteria = builder.createQuery(CarType.class);
        Root<CarType> root = criteria.from(CarType.class);

        // Not supposed to be a problem: there should not be a lot of car types.
        criteria.select(root);
        TypedQuery<CarType> query = manager.createQuery(criteria);

        return query.getResultList();
    }
}
