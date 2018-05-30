package hr.tvz.java.teambuildingbooking.controller;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.Reservation;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.ReservationForm;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import hr.tvz.java.teambuildingbooking.service.ReservationService;
import hr.tvz.java.teambuildingbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private static final String RESERVATION_CHECKOUT = "reservation/checkout";
    //private static final String SAVE_RESERVATION = "reservation/saveReservation";
    private static final String SAVE_SUCCESSFUL = "reservation/saveSuccessful";
    private static final String SAVE_UNSUCCESSFUL = "reservationUnsuccessful";

    @Autowired
    OfferService offerService;

    @Autowired
    UserService userService;

    @Autowired
    ReservationService reservationService;

    @GetMapping("/checkout")
    private  String reservationCheckout(Principal principal, Model model, @ModelAttribute("reservationForm")ReservationForm reservationForm) {
        User user = userService.findByUsername(principal.getName());
        Optional<Offer> offer = offerService.findOne(reservationForm.getOfferId());
        // TODO: Check if offer is valid!
        if (offer.isPresent() && user != null) {
            model.addAttribute("offer", offer.get());
            model.addAttribute("reservationForm", reservationForm);
        }

        return RESERVATION_CHECKOUT;
    }

    @PostMapping("/saveReservation")
    @Secured("USER")
    private String saveReservation(Principal principal, Model model, @ModelAttribute("reservationForm")ReservationForm reservationForm){
        User user = userService.findByUsername(principal.getName());
        Optional<Offer> offer = offerService.findOne(reservationForm.getOfferId());
        // TODO: Check if offer is valid (date, reserved, or already reserved by the same pearson)!...only registrated users can trigger this function
        if (offer.isPresent() && user != null) {
            try {
                Reservation reservation = reservationService.insertNewReservation(reservationForm, user);
                return SAVE_SUCCESSFUL;
            } catch (Exception e) {
                e.printStackTrace();
                return SAVE_UNSUCCESSFUL;
            }
        } else {
            return SAVE_UNSUCCESSFUL;
        }
    }
/*
    @RequestMapping("/saveSuccessful")
    private  String saveSuccessful() {

        return SAVE_SUCCESSFUL;
    }

    @RequestMapping("/saveUnsuccessful")
    private String saveUnsuccessful() {

        return SAVE_UNSUCCESSFUL;
    }
*/
}
