package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.mapper.OfferMapper;
import hr.tvz.java.teambuildingbooking.model.Category;
import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.OfferCategory;
import hr.tvz.java.teambuildingbooking.model.form.EditOfferForm;
import hr.tvz.java.teambuildingbooking.model.form.NewOfferForm;
import hr.tvz.java.teambuildingbooking.repository.CategoryRepository;
import hr.tvz.java.teambuildingbooking.repository.OfferCategoryRepository;
import hr.tvz.java.teambuildingbooking.repository.OfferRepository;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class OfferServiceImpl implements OfferService {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private OfferRepository offerRepository;

    private CategoryRepository categoryRepository;

    private OfferCategoryRepository offerCategoryRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, CategoryRepository categoryRepository, OfferCategoryRepository offerCategoryRepository) {
        this.offerRepository = offerRepository;
        this.categoryRepository = categoryRepository;
        this.offerCategoryRepository = offerCategoryRepository;
    }

    @Override
    public List<Offer> findAll() {
        return offerRepository.findAll();
    }

    @Override
    public Optional<Offer> findOne(Long id) {
        return offerRepository.findById(id);
    }

    @Override
    public List<Offer> findTopOffers() {
        return offerRepository.findTopOffers();
    }

    @Override
    public Offer createOffer(NewOfferForm newOfferForm) throws ParseException {
        Offer offer = OfferMapper.INSTANCE.newOfferFormToOffer(newOfferForm);

        Category selectedCategory = categoryRepository.findByName(newOfferForm.getCategory());
        Set<Category> categorySet = new HashSet<>();

        categorySet.add(selectedCategory);
        offer.setCategories(categorySet);
        offer.setActive(true);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date availableFrom = simpleDateFormat.parse(newOfferForm.getAvailableFrom());
        offer.setAvailableFrom(availableFrom);

        Date availableTo = simpleDateFormat.parse(newOfferForm.getAvailableUntil());
        offer.setAvailableFrom(availableTo);

        offer.setDateAdded(new Date());

        log.info("---> Adding offer with ID = " + offer.getId() + " to database ...");
        return offerRepository.save(offer);
    }

    @Transactional
    @Override
    public Offer editOffer(EditOfferForm editOfferForm) throws ParseException {
        Offer offer = OfferMapper.INSTANCE.editOfferFormToOffer(editOfferForm);

        offer.setName(editOfferForm.getName());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date availableFrom = simpleDateFormat.parse(editOfferForm.getAvailableFrom());
        Date availableTo = simpleDateFormat.parse(editOfferForm.getAvailableTo());
        offer.setAvailableFrom(availableFrom);
        offer.setAvailableTo(availableTo);

        if (!editOfferForm.getCategories().isEmpty()) {
            offerCategoryRepository.deleteOfferCategories(offer.getId());
            Set<Category> categories = updateCategories(editOfferForm.getCategories());
            offer.setCategories(categories);

            for (Category category : categories) {
                OfferCategory offerCategory = new OfferCategory();
                offerCategory.setCategoryId(category.getId());
                offerCategory.setOfferId(offer.getId());
                offerCategoryRepository.save(offerCategory);
            }

        }

        offerRepository.editOffer(offer.getAvailableFrom(), offer.getAvailableTo(), offer.getCity(), offer.getCountry(),
                offer.getDateLastEdited(), offer.getDescription(), offer.getMinNumberOfUsers(), offer.getMaxNumberOfUsers(),
                offer.getName(), offer.getId());
        return offer;
    }

    private Set<Category> updateCategories(List<String> categories) {

        Set<Category> categoriesSet = new HashSet<>();
        for (String c : categories) {
            Category category = categoryRepository.findByName(c);
            categoriesSet.add(category);
        }

        return categoriesSet;
    }
}
