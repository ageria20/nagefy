package ageria.nagefy.repositories;
import ageria.nagefy.entities.Cash;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CashCriteriaRepository {

    private final EntityManager entityManager;

    public CashCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Cash> findWithFilters(LocalDate startDate, LocalDate endDate, String paymentMethod, UUID staffId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cash> query = cb.createQuery(Cash.class);
        Root<Cash> cashRoot = query.from(Cash.class);

        List<Predicate> predicates = new ArrayList<>();

        if (startDate != null && endDate != null) {
            predicates.add(cb.between(cashRoot.get("createdAt"), startDate, endDate));
        }

        if (paymentMethod != null) {
            predicates.add(cb.equal(cashRoot.get("paymentMethod"), paymentMethod));
        }

        if (staffId != null) {
            Join<Object, Object> appointmentJoin = cashRoot.join("appointment");
            predicates.add(cb.equal(appointmentJoin.get("staff").get("id"), staffId));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }
}
