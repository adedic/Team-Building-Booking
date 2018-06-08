package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.Reservation;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.ReservationForm;
import hr.tvz.java.teambuildingbooking.repository.ReservationRepository;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import hr.tvz.java.teambuildingbooking.service.ReservationService;
import hr.tvz.java.teambuildingbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    OfferService offerService;

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public Reservation insertNewReservation(ReservationForm reservationForm, User user) {
        Optional<Offer> optional = offerService.findOne(reservationForm.getOfferId());
        Offer offer = optional.get();

        Reservation reservation = new Reservation();
        reservation.setOffer(offer);
        reservation.setUser(user);
        reservation.setDateOfReservation(reservationForm.getDate());
        reservation.setNumberOfUsers(reservationForm.getNumberOfUsers());
        reservation.setDateLastEdited(reservationForm.getDate());

        return reservationRepository.saveAndFlush(reservation);
    }

    @Override
    public List<Reservation> getAllReservationsByOffer(ReservationForm reservationForm) {
        List<Reservation> reservations = reservationRepository.getReservationsByOffer(reservationForm.getDate(), reservationForm.getOfferId());

        return reservations;
    }
}