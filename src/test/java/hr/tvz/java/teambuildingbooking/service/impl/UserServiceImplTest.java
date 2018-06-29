package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.model.Role;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.EditUserForm;
import hr.tvz.java.teambuildingbooking.model.form.RegistrationForm;
import hr.tvz.java.teambuildingbooking.service.UserService;
import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    private static final String EXISTING_USERNAME = "user";

    private static final String NON_EXISTING_USERNAME = "miromiric";

    private static final Long EXISTING_USER_ID = 1L;

    private static final Long NON_EXISTING_USER_ID = 99999L;

    private static final String EXISTING_EMAIL = "provider@booking.com";

    private static final String NON_EXISTING_EMAIL = "vojislav.seselj@mail.com";

    @Autowired
    private UserService userService;

    @Test
    public void UserServiceAutowired() {
        assertNotNull(userService);
    }

    @Test
    public void findByUsername_WhenUsernameExists() {
        // arrange ...

        // act ...
        User user = userService.findByUsername(EXISTING_USERNAME);

        // assert ...
        assertNotNull(user);
        assertEquals(EXISTING_USERNAME, user.getUsername());
    }

    @Test
    public void findByUsername_WhenUsernameDoesNotExist() {
        // arrange ...

        // act ...
        User user = userService.findByUsername(NON_EXISTING_USERNAME);

        // assert ...
        assertNull(user);
    }

    @Test
    public void createUser() throws ParseException {
        // arrange ...
        RegistrationForm registrationForm = new RegistrationForm();

        registrationForm.setUsername("Matica");
        registrationForm.setName("Mate");
        registrationForm.setPassword("pass");
        registrationForm.setConfirmPassword("pass");
        registrationForm.setSurname("Matić");
        registrationForm.setEmail("mate.mate@mate.ma");
        registrationForm.setUserRole("ROLE_PROVIDER");
        registrationForm.setDateOfBirth("2018-06-15");

        // act ...
        User user = userService.createUser(registrationForm);

        // assert ...
        assertNotNull(user);
        assertEquals(registrationForm.getUsername(), user.getUsername());
        assertEquals(registrationForm.getName(), user.getName());
        assertEquals(registrationForm.getSurname(), user.getSurname());
        assertEquals(registrationForm.getEmail(), user.getEmail());
    }


    @Test
    public void editUser() throws ParseException {
        // arrange ...
        EditUserForm editUserForm = new EditUserForm();
        editUserForm.setUsername("Jurica");
        editUserForm.setName("Jure");
        editUserForm.setSurname("Jurić");
        editUserForm.setEmail("jure@ju.re");
        editUserForm.setDateOfBirth("2018-06-15");
        String currentUserUsername = "user";

        // act ...
        int editUserResult = userService.editUser(editUserForm, currentUserUsername);

        // assert ...
        assertEquals(1, editUserResult);
    }

    @Test
    public void findRolesByUsername() {
        // arrange ...
        String username = "user";

        // act ...
        Set<Role> roleSet = userService.findRolesByUsername(username);
        List<Role> roles = new ArrayList<>();
        roles.addAll(roleSet);

        // assert ...
        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals("ROLE_USER", roles.get(0).getName());
    }

    @Test
    public void hasRole() {
        // arrange ...
        String username = "user";
        String role = "ROLE_USER";

        // act ...
        boolean userHasRole = userService.hasRole(username, role);

        // assert ...
        assertTrue(userHasRole);
    }

    @Test
    public void getById_WhenExists() {
        // arrange ...

        // act ...
        User user = userService.getById(EXISTING_USER_ID);

        // assert ...
        assertNotNull(user);
        assertEquals(EXISTING_USER_ID, user.getId());
    }

    @Test(expected = LazyInitializationException.class)
    public void getById_WhenDoesNotExist() {
        // arrange ...

        // act ...
        User user = userService.getById(NON_EXISTING_USER_ID);

        // assert ...
        assertNull(user);
    }

    @Test
    public void existsByEmailIgnoreCase_WhenExists() {
        // arrange ...

        // act ..
        boolean existsByMail = userService.existsByEmailIgnoreCase(EXISTING_EMAIL);

        // assert ...
        assertTrue(existsByMail);
    }

    @Test
    public void existsByEmailIgnoreCase_WhenDoesNotExist() {
        // arrange ...

        // act ..
        boolean existsByMail = userService.existsByEmailIgnoreCase(NON_EXISTING_EMAIL);

        // assert ...
        assertFalse(existsByMail);
    }

    @Test
    public void existsByUsernameIgnoreCase_WhenExists() {
        // arrange ...

        // act ..
        boolean existsByUsernameIgnoreCase = userService.existsByUsernameIgnoreCase(EXISTING_USERNAME);

        // assert ...
        assertTrue(existsByUsernameIgnoreCase);
    }

    @Test
    public void existsByUsernameIgnoreCase_WhenDoesNotExist() {
        // arrange ...

        // act ..
        boolean existsByUsernameIgnoreCase = userService.existsByUsernameIgnoreCase(NON_EXISTING_USERNAME);

        // assert ...
        assertFalse(existsByUsernameIgnoreCase);
    }
}