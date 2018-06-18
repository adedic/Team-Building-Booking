package hr.tvz.java.teambuildingbooking.repository.impl;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.criteria.SearchCriteria;
import hr.tvz.java.teambuildingbooking.repository.OfferDaoRepository;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class OfferDaoRepositoryImpl implements OfferDaoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Offer> findOffers(List<SearchCriteria> searchCriteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Offer> query = builder.createQuery(Offer.class);
        Root<Offer> rootOffer = query.from(Offer.class);

        Predicate predicate = builder.conjunction();

        for (SearchCriteria criteria : searchCriteria) {
            if (criteria.getOperation().equalsIgnoreCase(">")) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(rootOffer.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equalsIgnoreCase("<")) {
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(rootOffer.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equalsIgnoreCase(":")) {
                if (rootOffer.get(criteria.getKey()).getJavaType() == String.class) {
                    predicate = builder.and(predicate, builder.like(rootOffer.get(criteria.getKey()), "%" + criteria.getValue() + "%"));
                } else {
                    predicate = builder.and(predicate, builder.equal(rootOffer.get(criteria.getKey()), criteria.getValue()));
                }
            } else if (criteria.getOperation().equalsIgnoreCase(":>")) {
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(rootOffer.get(criteria.getKey()), (Date) criteria.getValue()));
            } else if (criteria.getOperation().equalsIgnoreCase(":<")) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(rootOffer.get(criteria.getKey()), (Date) criteria.getValue()));
            } else if (criteria.getOperation().equalsIgnoreCase("//")) {
                predicate = builder.and(predicate, builder.equal(rootOffer.join("categories").get("name"), criteria.getValue()));
            }

        }

        predicate = builder.and(predicate, builder.isNull(rootOffer.get("dateDeleted")));
        predicate = builder.and(predicate, builder.isTrue(rootOffer.get("active")));
        predicate = builder.and(predicate, builder.isTrue(rootOffer.get("enabled")));

        query.where(predicate);

        List<Offer> result = new ArrayList<>();
        try {
            result = entityManager.createQuery(query).getResultList();
        } catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

}
