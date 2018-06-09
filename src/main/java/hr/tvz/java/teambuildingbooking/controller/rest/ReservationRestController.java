package hr.tvz.java.teambuildingbooking.controller.rest;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.Reservation;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.ReservationForm;
import hr.tvz.java.teambuildingbooking.model.rest.Error;
import hr.tvz.java.teambuildingbooking.model.rest.RestReservationResponse;
import hr.tvz.java.teambuildingbooking.model.rest.UserReservation;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import hr.tvz.java.teambuildingbooking.service.ReservationService;
import hr.tvz.java.teambuildingbooking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/rest/reservation", produces="application/json")
@CrossOrigin(origins = "*")
public class ReservationRestController {

    private static final String RESERVATION_CHECKOUT = "reservation/checkout";
    private static final String SAVE_SUCCESSFUL = "reservation/saveSuccessful";
    private static final String SAVE_UNSUCCESSFUL = "reservation/saveUnsuccessful";

    private static final String MESSAGE_OFFER_VALID = "Valid";
    private static final String MESSAGE_OFFER_RESERVED = "Ponuda je već rezervirana za odabrani datum, molimo odaberite drugi datum ili ponudu!";
    private static final String MESSAGE_OFFER_RESSERVED_BY_THIS_USER = "Već ste rezervirali ovu ponudu!";
    private static final String MESSAGE_OFFER_INVALID = "Ponuda nije dostupna za odabrani datum/broj ljudi!";
    private static final String MESSAGE_OFFER_IS_NOT_AVAILABLE = "Ponuda nije dostupna.";

    private OfferService offerService;
    private UserService userService;
    private ReservationService reservationService;

    public ReservationRestController(OfferService offerService, UserService userService, ReservationService reservationService) {
        this.offerService = offerService;
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @PostMapping("/saveReservation")
    private ResponseEntity<RestReservationResponse> saveReservation(@RequestBody UserReservation userReservation){

        User user = userService.findByUsername(userReservation.getUsername());
        String isNewReservationValid = isNewReservationValid(userReservation.getReservationForm(), user);

        RestReservationResponse response = new RestReservationResponse();
        Error error = new Error();

        if (isNewReservationValid.equals(MESSAGE_OFFER_VALID)) {
            try {
                Reservation reservation = reservationService.insertNewReservation(userReservation.getReservationForm(), user);
                response.setMessage(SAVE_SUCCESSFUL);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } catch (Exception e) {
                error.setErrorId(1);
                error.setErrorMsg(SAVE_UNSUCCESSFUL);
                response.setError(error);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            error.setErrorId(2);
            error.setErrorMsg(MESSAGE_OFFER_INVALID);
            response.setError(error);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    private String isNewReservationValid(ReservationForm reservationForm, User user) {
        Optional<Offer> offer = offerService.findOne(reservationForm.getOfferId());

        if (offer.isPresent() && user != null) {
            if (offerService.isOfferValid(reservationForm)) {
                List<Reservation> reservations = reservationService.getAllReservationsByOffer(reservationForm);
                if (reservations.size() > 0) {
                    Optional<Reservation> reservedByThisUser = reservations.stream().findFirst().filter(r -> r.getUser().getId().equals(user.getId()));
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
