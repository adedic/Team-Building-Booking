package hr.tvz.java.teambuildingbooking.service;

import hr.tvz.java.teambuildingbooking.model.Reservation;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.ReservationForm;

import java.util.List;

public interface ReservationService {

    Reservation insertNewReservation(ReservationForm reservationForm, User user);

    List<Reservation> getAllReservationsByOffer(ReservationForm reservationForm);

}
