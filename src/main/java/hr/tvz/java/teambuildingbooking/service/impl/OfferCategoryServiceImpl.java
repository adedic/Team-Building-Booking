package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.model.OfferCategory;
import hr.tvz.java.teambuildingbooking.repository.OfferCategoryRepository;
import hr.tvz.java.teambuildingbooking.service.OfferCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferCategoryServiceImpl implements OfferCategoryService {

    @Autowired
    private OfferCategoryRepository offerCategoryRepository;

    @Override
    public List<OfferCategory> save(List<OfferCategory> offerCategories) {
        return offerCategoryRepository.saveAll(offerCategories);
    }

    @Override
    public void deleteOfferCategories(Long offerId) {
        offerCategoryRepository.deleteOfferCategories(offerId);
    }
}
