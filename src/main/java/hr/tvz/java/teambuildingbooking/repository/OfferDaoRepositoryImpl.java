package hr.tvz.java.teambuildingbooking.repository;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.criteria.SearchCriteria;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class OfferDaoRepositoryImpl implements OfferDaoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Offer> findOffers(List<SearchCriteria> searchCriteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Offer> query = builder.createQuery(Offer.class);
        Root r = query.from(Offer.class);

        Predicate predicate = builder.conjunction();

        for (SearchCriteria criteria : searchCriteria) {
            if (criteria.getOperation().equalsIgnoreCase(">")) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(r.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equalsIgnoreCase("<")) {
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(r.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equalsIgnoreCase(":")) {
                if (r.get(criteria.getKey()).getJavaType() == String.class) {
                    predicate = builder.and(predicate, builder.like(r.get(criteria.getKey()), "%" + criteria.getValue() + "%"));
                } else {
                    predicate = builder.and(predicate, builder.equal(r.get(criteria.getKey()), criteria.getValue()));
                }
            } else if (criteria.getOperation().equalsIgnoreCase(":>")) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(r.get(criteria.getKey()), (Date) criteria.getValue()));
            } else if (criteria.getOperation().equalsIgnoreCase(":<")) {
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(r.get(criteria.getKey()), (Date) criteria.getValue()));
            }

            predicate = builder.and(predicate, builder.isNull(r.get("dateDeleted")));
            predicate = builder.and(predicate, builder.isTrue(r.get("active")));
            predicate = builder.and(predicate, builder.isTrue(r.get("enabled")));
        }
        query.where(predicate);

        List<Offer> result = new ArrayList<>();
        try {
            result = entityManager.createQuery(query).getResultList();
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
