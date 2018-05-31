package hr.tvz.java.teambuildingbooking.service;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.EditOfferForm;
import hr.tvz.java.teambuildingbooking.model.form.NewOfferForm;
import hr.tvz.java.teambuildingbooking.model.form.ReservationForm;
import hr.tvz.java.teambuildingbooking.model.form.SearchOfferForm;

import java.security.Principal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OfferService {

    List<Offer> findAll();

    List<Offer> findOffers(SearchOfferForm searchOffer);

    List<Offer> findTopOffers();

    Optional<Offer> findOne(Long id);

    Offer createOffer(NewOfferForm newOfferForm, Principal principal) throws ParseException;

    Offer editOffer(EditOfferForm editOfferForm) throws ParseException;

    boolean isOfferValid(ReservationForm reservationForm);

}
