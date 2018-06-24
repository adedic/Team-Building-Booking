package hr.tvz.java.teambuildingbooking.utils;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.Reservation;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.ReservationForm;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import hr.tvz.java.teambuildingbooking.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ReservationUtility {

    public static final String MESSAGE_OFFER_VALID = "Valid";
    public static final String MESSAGE_OFFER_RESERVED = "Ponuda je već rezervirana za odabrani datum, molimo odaberite drugi datum ili ponudu!";
    public static final String MESSAGE_OFFER_RESSERVED_BY_THIS_USER = "Već ste rezervirali ovu ponudu!";
    public static final String MESSAGE_OFFER_INVALID_DATE = "Ponuda nije dostupna za odabrani datum!";
    public static final String MESSAGE_OFFER_NO_FREE_SPOTS = "Trenutno više nema slobodnih mjesta za označeni datum!";
    public static final String MESSAGE_OFFER_INVALID_NUM_OF_USERS = "Preostali broj slobodnih mjesta za navedeni datum je: ";
    public static final String MESSAGE_OFFER_INVALID = "Ponuda nije dostupna za navedeni datum ili oznaceni broj osoba!";
    public static final String MESSAGE_OFFER_IS_NOT_AVAILABLE = "Ponuda nije dostupna.";

    @Autowired
    OfferService offerService;

    @Autowired
    ReservationService reservationService;

    public String isNewReservationValid(ReservationForm reservationForm, User user) {
        Optional<Offer> offer = offerService.findOne(reservationForm.getOfferId());

        if (offer.isPresent() && user != null) {
            if (offerService.isOfferValid(reservationForm)) {
                List<Reservation> reservations = reservationService.getAllReservationsByOffer(reservationForm);
                if (!reservations.isEmpty()) {
                    Optional<Reservation> reservedByThisUser = reservations.stream().findFirst().filter(r -> r.getUser().getId() == user.getId());
                    if (reservedByThisUser.isPresent()) {
                        return MESSAGE_OFFER_RESSERVED_BY_THIS_USER;
                    } else {
                        int availableSpots = numberOfAvailableSpotsInOffer(offer.get(), reservationForm);
                        if (availableSpots < 1) {
                            return MESSAGE_OFFER_NO_FREE_SPOTS;
                        } else if (reservationForm.getNumberOfUsers() <= availableSpots) {
                            return MESSAGE_OFFER_VALID;
                        } else {
                            return MESSAGE_OFFER_INVALID_NUM_OF_USERS + availableSpots;
                        }
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

    public String checkIfOfferIsTaken(ReservationForm reservationForm, User user) {
        Optional<Offer> offer = offerService.findOne(reservationForm.getOfferId());

        if (offer.isPresent() && user != null) {
            List<Reservation> reservations = reservationService.getAllReservationsByOffer(reservationForm);
            if (reservations != null && !reservations.isEmpty()) {
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
            return MESSAGE_OFFER_IS_NOT_AVAILABLE;
        }
    }

    public int numberOfAvailableSpotsInOffer(Offer offer, ReservationForm reservationForm) {
        int maxUsers = offer.getMaxNumberOfUsers();
        List<Reservation> reservations = reservationService.getAllReservationsByOffer(reservationForm).stream().filter(reservation -> reservation.getDateOfReservation().compareTo(reservationForm.getDate()) == 0).collect(Collectors.toList());
        int currentNumberOfUsers = reservations.stream().mapToInt(reservation -> reservation.getNumberOfUsers()).sum();
        int availableSpots = maxUsers - currentNumberOfUsers;

        if (availableSpots > 0) {
            if (availableSpots >= offer.getMinNumberOfUsers()) {
                return availableSpots;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

}
