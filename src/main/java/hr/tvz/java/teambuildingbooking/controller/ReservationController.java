package hr.tvz.java.teambuildingbooking.controller;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.Reservation;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.ReservationForm;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import hr.tvz.java.teambuildingbooking.service.ReservationService;
import hr.tvz.java.teambuildingbooking.service.UserService;
import hr.tvz.java.teambuildingbooking.utils.ReservationUtility;
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

    private static final String ERROR_MESSAGE_ATR_NAME = "errorMessage";

    private static final String RESERVATION_CHECKOUT = "reservation/checkout";
    private static final String SAVE_SUCCESSFUL = "reservation/saveSuccessful";
    private static final String SAVE_UNSUCCESSFUL = "reservation/saveUnsuccessful";

    @Autowired
    OfferService offerService;

    @Autowired
    UserService userService;

    @Autowired
    ReservationService reservationService;

    @Autowired
    ReservationUtility reservationUtility;

    @GetMapping("/checkout")
    @Secured("USER")
    private  String reservationCheckout(Principal principal, Model model, @ModelAttribute("reservationForm")ReservationForm reservationForm) {
        Optional<Offer> offer = offerService.findOne(reservationForm.getOfferId());
        if (offer.isPresent()) {
            model.addAttribute("offer", offer.get());
            model.addAttribute("reservationForm", reservationForm);
        }

        User user = userService.findByUsername(principal.getName());
        String isNewReservationValid = reservationUtility.isNewReservationValid(reservationForm, user);

        if (isNewReservationValid != ReservationUtility.MESSAGE_OFFER_VALID) {
            model.addAttribute(ERROR_MESSAGE_ATR_NAME ,isNewReservationValid);
        }

        return RESERVATION_CHECKOUT;
    }

    @PostMapping("/saveReservation")
    @Secured("USER")
    private String saveReservation(Principal principal, Model model, @ModelAttribute("reservationForm")ReservationForm reservationForm){

        User user = userService.findByUsername(principal.getName());
        String isNewReservationValid = reservationUtility.isNewReservationValid(reservationForm, user);

        if (isNewReservationValid.equals("Valid")) {
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
}
