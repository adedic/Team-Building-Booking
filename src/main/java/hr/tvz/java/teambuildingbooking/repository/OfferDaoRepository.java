package hr.tvz.java.teambuildingbooking.repository;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.criteria.SearchCriteria;

import java.util.List;

public interface OfferDao {

    List<Offer> findOffers(List<SearchCriteria> searchCriteria);

}
