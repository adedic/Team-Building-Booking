package hr.tvz.java.teambuildingbooking.mapper;

import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.EditUserForm;
import hr.tvz.java.teambuildingbooking.model.form.RegistrationForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Test
    public void registrationFormToUser() {
        RegistrationForm registrationForm = new RegistrationForm();

        registrationForm.setUsername("Matica");
        registrationForm.setName("Mate");
        registrationForm.setPassword("pass");
        registrationForm.setConfirmPassword("pass");
        registrationForm.setSurname("Matić");
        registrationForm.setEmail("mate.mate@mate.ma");
        registrationForm.setUserRole("ROLE_PROVIDER");
        registrationForm.setDateOfBirth("2018-06-15");
        assertNotNull(UserMapper.INSTANCE.registrationFormToUser(registrationForm));
    }

    @Test
    public void editUserFormToUser() {
        EditUserForm editUserForm = new EditUserForm();
        editUserForm.setUsername("Jurica");
        editUserForm.setName("Jure");
        editUserForm.setSurname("Jurić");
        editUserForm.setEmail("jure@ju.re");
        editUserForm.setDateOfBirth("2018-06-15");
        assertNotNull(UserMapper.INSTANCE.editUserFormToUser(editUserForm));

    }

    @Test
    public void userToUserEditForm() {
        User user = new User();
        user.setUsername("Matica");
        user.setName("Mate");
        user.setPassword("pass");
        user.setSurname("Matić");
        user.setEmail("mate.mate@mate.ma");
        user.setDateOfBirth(new Date());
        assertNotNull(UserMapper.INSTANCE.userToUserEditForm(user));
    }
}