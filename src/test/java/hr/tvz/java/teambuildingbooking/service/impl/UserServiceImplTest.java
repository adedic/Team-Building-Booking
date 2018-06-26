package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.mapper.UserMapper;
import hr.tvz.java.teambuildingbooking.model.Role;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.RegistrationForm;
import hr.tvz.java.teambuildingbooking.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @MockBean
    UserService userService;

    @Test
    public void UserServiceAutowired() {
        assertNotNull(userService);
    };

    @Test
    public void findByUsername() {
        String username = "user";
        Mockito.when(userService.findByUsername(username)).thenReturn(new User());
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

        User user = UserMapper.INSTANCE.registrationFormToUser(registrationForm);
        user.setPassword(new BCryptPasswordEncoder().encode(registrationForm.getPassword()));
        user.setDateOfBirth(new Date());
        user.setDateOfRegistration(new Date());
        user.setEnabled(true);

        Mockito.when(userService.createUser(registrationForm)).thenReturn(user);
    }

    @Test
    public void editUser() {
    }

    @Test
    public void existsByUsernameIgnoreCase() {
    }

    @Test
    public void existsByEmailIgnoreCase() {
    }

    @Test
    public void getById() {
    }

    @Test
    public void findRolesByUsername() {
    }

    @Test
    public void hasRole() {
    }

    @Test
    public void findAll() {
    }
}