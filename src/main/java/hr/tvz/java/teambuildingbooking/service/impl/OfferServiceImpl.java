package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.mapper.OfferMapper;
import hr.tvz.java.teambuildingbooking.model.*;
import hr.tvz.java.teambuildingbooking.model.criteria.SearchCriteria;
import hr.tvz.java.teambuildingbooking.model.form.EditOfferForm;
import hr.tvz.java.teambuildingbooking.model.form.NewOfferForm;
import hr.tvz.java.teambuildingbooking.model.form.ReservationForm;
import hr.tvz.java.teambuildingbooking.model.form.SearchOfferForm;
import hr.tvz.java.teambuildingbooking.repository.CategoryRepository;
import hr.tvz.java.teambuildingbooking.repository.OfferDaoRepository;
import hr.tvz.java.teambuildingbooking.repository.OfferRepository;
import hr.tvz.java.teambuildingbooking.repository.ReservationRepository;
import hr.tvz.java.teambuildingbooking.service.OfferPictureService;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import hr.tvz.java.teambuildingbooking.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class OfferServiceImpl implements OfferService {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_FORMAT_2 = "dd.MM.yyyy";

    private OfferRepository offerRepository;
    private CategoryRepository categoryRepository;
    private ReservationRepository reservationRepository;

    private OfferDaoRepository offerDaoRepository;

    private UserService userService;

    private OfferPictureService offerPictureService;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, OfferDaoRepository offerDaoRepository,
                            CategoryRepository categoryRepository, UserService userService, OfferPictureService offerPictureService, ReservationRepository reservationRepository) {
        this.offerRepository = offerRepository;
        this.offerDaoRepository = offerDaoRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.offerPictureService = offerPictureService;
        this.reservationRepository = reservationRepository;
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
        if (searchOffer != null) {
            if (searchOffer.getCategory() != null && !searchOffer.getCategory().equals("")) {
                searchCriteria.add(new SearchCriteria("categoryId", "//", searchOffer.getCategory()));
            }
            if (searchOffer.getCity() != null && !searchOffer.getCity().equals("")) {
                searchCriteria.add(new SearchCriteria("city", ":", searchOffer.getCity()));
            }
            if (searchOffer.getCountry() != null && !searchOffer.getCountry().equals("")) {
                searchCriteria.add(new SearchCriteria("country", ":", searchOffer.getCountry()));
            }
            if (searchOffer.getNumOfPeople() != null) {
                searchCriteria.add(new SearchCriteria("minNumberOfUsers", "<", searchOffer.getNumOfPeople()));
                searchCriteria.add(new SearchCriteria("maxNumberOfUsers", ">", searchOffer.getNumOfPeople()));
            }
            if (searchOffer.getDate() != null && !searchOffer.getDate().equals("")) {
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

        List<Offer> offerList = offerDaoRepository.findOffers(searchCriteria);
        List<Offer> finalList = new ArrayList<>();

        for(Offer offer : offerList) {
            Date date = null;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_2);
            try {
                date = simpleDateFormat.parse(searchOffer.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            List<Reservation> reservationList = reservationRepository.getReservationsByOffer(date, offer.getId());

            Integer numOfPeople = 0;
            for(Reservation reservation : reservationList) {
                numOfPeople += reservation.getNumberOfUsers();
            }

            if(searchOffer.getNumOfPeople() != null) {
                if ((numOfPeople + searchOffer.getNumOfPeople()) <= offer.getMaxNumberOfUsers()) {
                    finalList.add(offer);
                }
            } else {
                finalList.add(offer);
            }
        }

        return finalList;
    }

    @Override
    public boolean isOfferValid(ReservationForm reservationForm) {
        List<SearchCriteria> searchCriteria = new ArrayList<>();
        if (reservationForm != null) {
            if (reservationForm.getOfferId() != null) {
                searchCriteria.add(new SearchCriteria("id", ":", reservationForm.getOfferId()));
            }
            if (reservationForm.getDate() != null) {
                searchCriteria.add(new SearchCriteria("availableFrom", ":>", reservationForm.getDate()));
                searchCriteria.add(new SearchCriteria("availableTo", ":<", reservationForm.getDate()));
            }
            if (reservationForm.getNumberOfUsers() != null) {
                searchCriteria.add(new SearchCriteria("minNumberOfUsers", "<", reservationForm.getNumberOfUsers()));
                searchCriteria.add(new SearchCriteria("maxNumberOfUsers", ">", reservationForm.getNumberOfUsers()));
            }
        }

        List<Offer> offers = offerDaoRepository.findOffers(searchCriteria);
        if (offers.size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Offer save(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public List<Offer> findTopOffers() {
        return offerRepository.findTopOffers();
    }

    @Override
    public List<Offer> findOffersByUserOrderByDateAdded(User user) {
        log.info("---> Fetching offers for user " + user.getUsername());
        return offerRepository.findAllByUserOrderByDateAddedDesc(user);
    }

    @Override
    public Offer createOffer(NewOfferForm newOfferForm, String base64String, String name, Integer size, String username) throws ParseException, IOException {
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

        Date availableTo = simpleDateFormat.parse(newOfferForm.getAvailableTo());
        offer.setAvailableTo(availableTo);

        offer.setDateAdded(new Date());
        offer.setDateLastEdited(new Date());
        offer.setUser(userService.findByUsername(username));
        offer.setName(newOfferForm.getName());
        offer.setAddress(newOfferForm.getAddress());

        if (!base64String.isEmpty()) {
            OfferPicture offerPicture = new OfferPicture(base64String, name, size);
            offerPictureService.save(offerPicture);
            offer.setOfferPicture(offerPicture);
        }

        Offer savedOffer = offerRepository.save(offer);
        log.info("---> Adding offer with ID = " + offer.getId() + " to database ...");
        return savedOffer;
    }

    @Transactional
    @Modifying
    @Override
    public Offer editOffer(EditOfferForm editOfferForm, String base64String, String name, Integer size, String username) throws ParseException, IOException {
        Offer offer = OfferMapper.INSTANCE.editOfferFormToOffer(editOfferForm);

        offer.setName(editOfferForm.getName());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date availableFrom = simpleDateFormat.parse(editOfferForm.getAvailableFrom());
        Date availableTo = simpleDateFormat.parse(editOfferForm.getAvailableTo());
        Date dateAdded = simpleDateFormat.parse(editOfferForm.getDateAdded());
        offer.setAvailableFrom(availableFrom);
        offer.setAvailableTo(availableTo);
        offer.setDateAdded(dateAdded);

        if (!editOfferForm.getCategories().isEmpty()) {
            Set<Category> categories = updateCategories(editOfferForm.getCategories());
            offer.setCategories(categories);
        }

        offer.setUser(userService.findByUsername(username));

        if (!base64String.isEmpty() && size > 0) {
            OfferPicture offerPicture = new OfferPicture(base64String, name, size);
            offerPictureService.save(offerPicture);
            offer.setOfferPicture(offerPicture);
        } else {
            Long offerPictureId = offerRepository.getOfferPictureIdByOfferId(offer.getId());
            OfferPicture offerPicture = offerPictureService.findById(offerPictureId);
            offer.setOfferPicture(offerPicture);
        }

        offer.setAddress(editOfferForm.getAddress());

        return offerRepository.save(offer);
    }

    @Override
    public Long getOfferPictureIdByOfferId(Long id) {
        return offerRepository.getOfferPictureIdByOfferId(id);
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
