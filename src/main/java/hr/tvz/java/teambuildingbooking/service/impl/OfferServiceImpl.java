package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.criteria.SearchCriteria;
import hr.tvz.java.teambuildingbooking.model.form.SearchOfferForm;
import hr.tvz.java.teambuildingbooking.repository.OfferDaoRepository;
import hr.tvz.java.teambuildingbooking.repository.OfferRepository;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OfferServiceImpl implements OfferService {

    private OfferRepository offerRepository;

    private OfferDaoRepository offerDaoRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, OfferDaoRepository offerDaoRepository) {
        this.offerRepository = offerRepository;
        this.offerDaoRepository = offerDaoRepository;
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

            }
            if(searchOffer.getCity() != null) {
                searchCriteria.add(new SearchCriteria("city", ":", searchOffer.getCity()));
            }
            if(searchOffer.getCountry() != null) {
                searchCriteria.add(new SearchCriteria("country", ":", searchOffer.getCountry()));
            }
            if(searchOffer.getNumOfPeople() != null) {
                searchCriteria.add(new SearchCriteria("minNumberOfUsers", ">", searchOffer.getNumOfPeople()));
                searchCriteria.add(new SearchCriteria("maxNumberOfUsers", "<", searchOffer.getNumOfPeople()));
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
}
