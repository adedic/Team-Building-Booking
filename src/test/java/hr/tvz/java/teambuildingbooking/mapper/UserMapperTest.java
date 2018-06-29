package hr.tvz.java.teambuildingbooking.mapper;

import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.EditUserForm;
import hr.tvz.java.teambuildingbooking.model.form.RegistrationForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Test
    public void registrationFormToUser() {
        // arrange ...
        RegistrationForm registrationForm = new RegistrationForm();

        registrationForm.setUsername("Matica");
        registrationForm.setName("Mate");
        registrationForm.setPassword("pass");
        registrationForm.setSurname("Matić");
        registrationForm.setEmail("mate.mate@mate.ma");

        // act ...
        User user = UserMapper.INSTANCE.registrationFormToUser(registrationForm);

        // assert ...
        assertNotNull(user);
        assertEquals(registrationForm.getUsername(), user.getUsername());
        assertEquals(registrationForm.getName(), user.getName());
        assertEquals(registrationForm.getPassword(), user.getPassword());
        assertEquals(registrationForm.getSurname(), user.getSurname());
        assertEquals(registrationForm.getEmail(), user.getEmail());
    }

    @Test
    public void editUserFormToUser() {
        // arrange ...
        EditUserForm editUserForm = new EditUserForm();

        editUserForm.setUsername("Jurica");
        editUserForm.setName("Jure");
        editUserForm.setSurname("Jurić");
        editUserForm.setEmail("jure@ju.re");

        // act ...
        User user = UserMapper.INSTANCE.editUserFormToUser(editUserForm);

        // assert ...
        assertNotNull(user);
        assertEquals(editUserForm.getUsername(), user.getUsername());
        assertEquals(editUserForm.getName(), user.getName());
        assertEquals(editUserForm.getSurname(), user.getSurname());
        assertEquals(editUserForm.getEmail(), user.getEmail());

    }

    @Test
    public void userToUserEditForm() {
        // arrange ...
        User user = new User();
        user.setUsername("Matica");
        user.setName("Mate");
        user.setSurname("Matić");
        user.setEmail("mate.mate@mate.ma");

        // act ...
        EditUserForm editUserForm = UserMapper.INSTANCE.userToUserEditForm(user);

        // assert ...
        assertNotNull(editUserForm);
        assertEquals(editUserForm.getUsername(), user.getUsername());
        assertEquals(editUserForm.getName(), user.getName());
        assertEquals(editUserForm.getSurname(), user.getSurname());
        assertEquals(editUserForm.getEmail(), user.getEmail());

    }
}