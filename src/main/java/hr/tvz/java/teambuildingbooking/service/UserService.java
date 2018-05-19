package hr.tvz.java.teambuildingbooking.service;

import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.EditUserForm;
import hr.tvz.java.teambuildingbooking.model.form.RegistrationForm;

import java.text.ParseException;

public interface UserService {

    User findByUsername(String username);

    User createUser(RegistrationForm form) throws ParseException;

    void editUser(EditUserForm editUserForm, String currentUserUsername) throws ParseException;

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);

    User getById(Long id);
}
