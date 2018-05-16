package hr.tvz.java.teambuildingbooking.service;

import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.RegistrationForm;

import java.text.ParseException;

public interface UserService {

    User createUser(RegistrationForm form) throws ParseException;

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);
}
