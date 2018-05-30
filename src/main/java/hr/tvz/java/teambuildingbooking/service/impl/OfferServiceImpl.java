package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.mapper.OfferMapper;
import hr.tvz.java.teambuildingbooking.model.Category;
import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.OfferCategory;
import hr.tvz.java.teambuildingbooking.model.criteria.SearchCriteria;
import hr.tvz.java.teambuildingbooking.model.form.EditOfferForm;
import hr.tvz.java.teambuildingbooking.model.form.NewOfferForm;
import hr.tvz.java.teambuildingbooking.model.form.SearchOfferForm;
import hr.tvz.java.teambuildingbooking.repository.CategoryRepository;
import hr.tvz.java.teambuildingbooking.repository.OfferCategoryRepository;
import hr.tvz.java.teambuildingbooking.repository.OfferDaoRepository;
import hr.tvz.java.teambuildingbooking.repository.OfferRepository;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import hr.tvz.java.teambuildingbooking.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class OfferServiceImpl implements OfferService {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private OfferRepository offerRepository;

    private OfferDaoRepository offerDaoRepository;

    private CategoryRepository categoryRepository;

    private OfferCategoryRepository offerCategoryRepository;

    private UserService userService;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, OfferDaoRepository offerDaoRepository, CategoryRepository categoryRepository, OfferCategoryRepository offerCategoryRepository, UserService userService) {
        this.offerRepository = offerRepository;
        this.offerDaoRepository = offerDaoRepository;
        this.categoryRepository = categoryRepository;
        this.offerCategoryRepository = offerCategoryRepository;
        this.userService = userService;
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
    public List<Offer> findOffers(SearchOfferForm searchOffer) {
        List<SearchCriteria> searchCriteria = new ArrayList<>();
        if(searchOffer != null) {
            if(searchOffer.getCategory() != null) {
                searchCriteria.add(new SearchCriteria("categoryId", "//", searchOffer.getCategory()));
            }
            if(searchOffer.getCity() != null && !searchOffer.getCity().equals("")) {
                searchCriteria.add(new SearchCriteria("city", ":", searchOffer.getCity()));
            }
            if(searchOffer.getCountry() != null && !searchOffer.getCountry().equals("")) {
                searchCriteria.add(new SearchCriteria("country", ":", searchOffer.getCountry()));
            }
            if(searchOffer.getNumOfPeople() != null) {
                searchCriteria.add(new SearchCriteria("minNumberOfUsers", "<", searchOffer.getNumOfPeople()));
                searchCriteria.add(new SearchCriteria("maxNumberOfUsers", ">", searchOffer.getNumOfPeople()));
            }
            if(searchOffer.getDate() != null) {
                Date date1 = null;
                SimpleDateFormat tDateFormatter1 = new SimpleDateFormat("dd.MM.yyyy");
                try {
                    date1 = tDateFormatter1.parse(searchOffer.getDate());
                } catch (ParseException pE) {
                }
                searchCriteria.add(new SearchCriteria("availableFrom", ":>", date1));
                searchCriteria.add(new SearchCriteria("availableTo", ":<", date1));
            }
        }
        return offerDaoRepository.findOffers(searchCriteria);
    }

    @Override
    public List<Offer> findTopOffers() {
        return offerRepository.findTopOffers();
    }

    @Override
    public Offer createOffer(NewOfferForm newOfferForm, Principal principal) throws ParseException {
        Offer offer = OfferMapper.INSTANCE.newOfferFormToOffer(newOfferForm);

        Set<Category> categoriesSet = new HashSet<>();
        if (!newOfferForm.getCategories().isEmpty()) {
            for (String categoryName : newOfferForm.getCategories()) {
                Category selectedCategory = categoryRepository.findByName(categoryName);
                categoriesSet.add(selectedCategory);
            }
        }

        offer.setCategories(categoriesSet);
        offer.setActive(true);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date availableFrom = simpleDateFormat.parse(newOfferForm.getAvailableFrom());
        offer.setAvailableFrom(availableFrom);

        Date availableTo = simpleDateFormat.parse(newOfferForm.getAvailableUntil());
        offer.setAvailableTo(availableTo);

        offer.setDateAdded(new Date());
        offer.setDateLastEdited(new Date());
        offer.setUser(userService.findByUsername(principal.getName()));
        offer.setName(newOfferForm.getName());

        Offer savedOffer = offerRepository.save(offer);
        log.info("---> Adding offer with ID = " + offer.getId() + " to database ...");
        return savedOffer;
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
