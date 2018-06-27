package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.model.form.EditUserForm;
import hr.tvz.java.teambuildingbooking.model.form.RegistrationForm;
import hr.tvz.java.teambuildingbooking.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    public void UserServiceAutowired() {
        assertNotNull(userService);
    };

    @Test
    public void findByUsername() {
        String username = "user";
        assertNotNull(userService.findByUsername(username));
    }


    @Test
    public void createUser() throws ParseException {
        RegistrationForm registrationForm = new RegistrationForm();

        registrationForm.setUsername("Matica");
        registrationForm.setName("Mate");
        registrationForm.setPassword("pass");
        registrationForm.setConfirmPassword("pass");
        registrationForm.setSurname("Matić");
        registrationForm.setEmail("mate.mate@mate.ma");
        registrationForm.setUserRole("ROLE_PROVIDER");
        registrationForm.setDateOfBirth("2018-06-15");

        assertNotNull(userService.createUser(registrationForm));
    }


    @Test
    public void editUser() throws ParseException {
        EditUserForm editUserForm = new EditUserForm();
        editUserForm.setUsername("Jurica");
        editUserForm.setName("Jure");
        editUserForm.setSurname("Jurić");
        editUserForm.setEmail("jure@ju.re");
        editUserForm.setDateOfBirth("2018-06-15");
        String currentUserUsername = "user";

        assertNotNull(userService.editUser(editUserForm, currentUserUsername));
    }

    @Test
    public void findRolesByUsername() {
        String username = "user";
        assertNotNull(userService.findRolesByUsername(username));
    }

    @Test
    public void hasRole() {
        String username = "user";
        String role = "ROLE_USER";
        assertTrue(userService.hasRole(username, role));
    }

    @Test
    public void getById() {
        assertNotNull(userService.getById(new Long(1)));
    }

    @Test
    public void existsByEmailIgnoreCase() {
        String email = "blabla";
        assertFalse(userService.existsByEmailIgnoreCase(email));
    }

    @Test
    public void existsByUsernameIgnoreCase() {
        String username = "Matica";
        assertFalse(userService.existsByUsernameIgnoreCase(username));
    }

    @Test
    public void findAll() {
        assertNotNull(userService.findAll());
    }

}