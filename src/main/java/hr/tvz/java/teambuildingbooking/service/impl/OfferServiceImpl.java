package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.mapper.OfferMapper;
import hr.tvz.java.teambuildingbooking.model.Category;
import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.form.EditOfferForm;
import hr.tvz.java.teambuildingbooking.model.form.NewOfferForm;
import hr.tvz.java.teambuildingbooking.repository.CategoryRepository;
import hr.tvz.java.teambuildingbooking.repository.OfferRepository;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hibernate.type.descriptor.java.JdbcDateTypeDescriptor.DATE_FORMAT;

@Slf4j
@Service
public class OfferServiceImpl implements OfferService {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private OfferRepository offerRepository;

    private CategoryRepository categoryRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
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
        offer.setRoles(categorySet);
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

        offer.setDateLastEdited(new Date());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date availableFrom = simpleDateFormat.parse(editOfferForm.getAvailableFrom());
        Date availableTo = simpleDateFormat.parse(editOfferForm.getAvailableTo());

        offer.setAvailableFrom(availableFrom);
        offer.setAvailableTo(availableTo);

        offerRepository.editOffer(offer.getAvailableFrom(), offer.getAvailableTo(), offer.getCity(), offer.getCountry(),
                offer.getDateLastEdited(), offer.getDescription(), offer.getMinNumberOfUsers(), offer.getMaxNumberOfUsers(),
                offer.getName(), offer.getId());

        return offer;
    }
}
