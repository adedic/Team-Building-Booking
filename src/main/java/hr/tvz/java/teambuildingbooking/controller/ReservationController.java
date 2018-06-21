package hr.tvz.java.teambuildingbooking.controller;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.Reservation;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.ReservationForm;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import hr.tvz.java.teambuildingbooking.service.ReservationService;
import hr.tvz.java.teambuildingbooking.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private static final String RESERVATION_CHECKOUT = "reservation/checkout";
    //private static final String SAVE_RESERVATION = "reservation/saveReservation"; TODO
    private static final String SAVE_SUCCESSFUL = "reservation/saveSuccessful";
    private static final String SAVE_UNSUCCESSFUL = "reservation/saveUnsuccessful";

    private static final String MESSAGE_OFFER_VALID = "Valid";
    private static final String MESSAGE_OFFER_RESERVED = "Ponuda je već rezervirana za odabrani datum, molimo odaberite drugi datum ili ponudu!";
    private static final String MESSAGE_OFFER_RESSERVED_BY_THIS_USER = "Već ste rezervirali ovu ponudu!";
    private static final String MESSAGE_OFFER_INVALID = "Ponuda nije dostupna za odabrani datum/broj ljudi!";
    private static final String MESSAGE_OFFER_IS_NOT_AVAILABLE = "Ponuda nije dostupna.";
    private static final String ERROR_MESSAGE_ATR_NAME = "errorMessage";

    @Autowired
    OfferService offerService;

    @Autowired
    UserService userService;

    @Autowired
    ReservationService reservationService;

    @GetMapping("/checkout")
    private  String reservationCheckout(Principal principal, Model model, @ModelAttribute("reservationForm")ReservationForm reservationForm) {
        Optional<Offer> offer = offerService.findOne(reservationForm.getOfferId());
        if (offer.isPresent()) {
            model.addAttribute("offer", offer.get());
            model.addAttribute("reservationForm", reservationForm);
        }

        // user is not logged in or session has been expired
        if (principal == null) {
            String errorMessage = "Ne možete rezervirati ponudu dok se ne prijavite u aplikaciju!";
            model.addAttribute(ERROR_MESSAGE_ATR_NAME, errorMessage);
            return RESERVATION_CHECKOUT;
        }

        User user = userService.findByUsername(principal.getName());
        String isNewReservationValid = isNewReservationValid(reservationForm, user);

        if (isNewReservationValid != MESSAGE_OFFER_VALID) {
            model.addAttribute(ERROR_MESSAGE_ATR_NAME ,isNewReservationValid);
        }

        return RESERVATION_CHECKOUT;
    }

    @PostMapping("/saveReservation")
    @Secured("USER")
    // @Secured not working when coding this
    private String saveReservation(Principal principal, Model model, @ModelAttribute("reservationForm")ReservationForm reservationForm){
        // user is not logged in or session has been expired
        // use untill "@Secured" is not repaired
        if (principal == null) {
            String errorMessage = "Molimo Vas provjerite da li ste prijavljeni u aplikaciju!";
            model.addAttribute(ERROR_MESSAGE_ATR_NAME, errorMessage);
            return SAVE_UNSUCCESSFUL;
        }
        User user = userService.findByUsername(principal.getName());
        String isNewReservationValid = isNewReservationValid(reservationForm, user);

        if (isNewReservationValid == MESSAGE_OFFER_VALID) {
            try {
                reservationService.insertNewReservation(reservationForm, user);
                return SAVE_SUCCESSFUL;
            } catch (Exception e) {
                log.info(e.getMessage());
                return SAVE_UNSUCCESSFUL;
            }
        } else {
            model.addAttribute(ERROR_MESSAGE_ATR_NAME, isNewReservationValid);
            return SAVE_UNSUCCESSFUL;
        }
    }

    private String isNewReservationValid(ReservationForm reservationForm, User user) {
        Optional<Offer> offer = offerService.findOne(reservationForm.getOfferId());

        if (offer.isPresent() && user != null) {
            if (offerService.isOfferValid(reservationForm)) {
                List<Reservation> reservations = reservationService.getAllReservationsByOffer(reservationForm);
                if (!reservations.isEmpty()) {
                    Optional<Reservation> reservedByThisUser = reservations.stream().findFirst().filter(r -> r.getUser().getId() == user.getId());
                    if (reservedByThisUser.isPresent()) {
                        return MESSAGE_OFFER_RESSERVED_BY_THIS_USER;
                    } else {
                        return MESSAGE_OFFER_RESERVED;
                    }
                } else {
                    return MESSAGE_OFFER_VALID;
                }
            } else {
                return MESSAGE_OFFER_INVALID;
            }
        } else {
            return MESSAGE_OFFER_IS_NOT_AVAILABLE;
        }
    }
}
