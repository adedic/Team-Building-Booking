package hr.tvz.java.teambuildingbooking.service;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.EditOfferForm;
import hr.tvz.java.teambuildingbooking.model.form.NewOfferForm;
import hr.tvz.java.teambuildingbooking.model.form.ReservationForm;
import hr.tvz.java.teambuildingbooking.model.form.SearchOfferForm;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface OfferService {

    List<Offer> findAll();

    List<Offer> findOffers(SearchOfferForm searchOffer);

    List<Offer> findTopOffers();

    Optional<Offer> findOne(Long id);

    Offer createOffer(NewOfferForm newOfferForm, String base64String, String name, Integer size, String username) throws ParseException, IOException;

    Long getOfferPictureIdByOfferId(Long id);

    List<Offer> findOffersByUserOrderByDateAdded(User user);

    Offer editOffer(EditOfferForm editOfferForm, String base64String, String name, Integer size, String username) throws ParseException, IOException;

    boolean isOfferValid(ReservationForm reservationForm);

    Offer save(Offer offer);
}
