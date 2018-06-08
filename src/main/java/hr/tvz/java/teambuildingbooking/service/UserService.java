package hr.tvz.java.teambuildingbooking.service;

import hr.tvz.java.teambuildingbooking.model.Role;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.EditUserForm;
import hr.tvz.java.teambuildingbooking.model.form.RegistrationForm;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

public interface UserService {

    User findByUsername(String username);

    User createUser(RegistrationForm form) throws ParseException;

    void editUser(EditUserForm editUserForm, String currentUserUsername) throws ParseException;

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);

    User getById(Long id);

    Set<Role> findRolesByUsername(String username);

    boolean hasRole(String username, String role);

    List<User> findAll();
}
