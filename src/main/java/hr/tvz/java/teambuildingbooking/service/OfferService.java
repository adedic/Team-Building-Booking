package hr.tvz.java.teambuildingbooking.service;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.form.NewOfferForm;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface OfferService {

    List<Offer> findAll();

    List<Offer> findTopOffers();

    Optional<Offer> findOne(Long id);

    Offer createOffer(NewOfferForm newOfferForm) throws ParseException;
}
