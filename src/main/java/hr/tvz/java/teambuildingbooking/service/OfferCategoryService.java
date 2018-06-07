package hr.tvz.java.teambuildingbooking.service;

import hr.tvz.java.teambuildingbooking.model.OfferCategory;

import java.util.List;

public interface OfferCategoryService {

    List<OfferCategory> save(List<OfferCategory> offerCategories);

    void deleteOfferCategories(Long offerId);
}
