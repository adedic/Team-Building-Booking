package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.ReservationForm;
import hr.tvz.java.teambuildingbooking.service.CategoryService;
import hr.tvz.java.teambuildingbooking.service.ReservationService;
import hr.tvz.java.teambuildingbooking.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceImplTest {

    @Autowired
    ReservationService reservationService;

    @Autowired
    UserService userService;

    @Test
    public void insertNewReservation() {
        ReservationForm reservationForm = new ReservationForm();
        reservationForm.setOfferId(new Long(1));
        reservationForm.setDate(new Date());
        reservationForm.setNumberOfUsers(5);
        reservationForm.setDateString("");

        User user = new User();
        user = userService.getById(new Long(1));

        assertNotNull(reservationService.insertNewReservation(reservationForm, user));

    }

    @Test
    public void getAllReservationsByOffer() {
        ReservationForm reservationForm = new ReservationForm();
        reservationForm.setOfferId(new Long(1));
        reservationForm.setDate(new Date());
        reservationForm.setNumberOfUsers(5);
        reservationForm.setDateString("");

        assertNotNull(reservationService.getAllReservationsByOffer(reservationForm));
    }
}