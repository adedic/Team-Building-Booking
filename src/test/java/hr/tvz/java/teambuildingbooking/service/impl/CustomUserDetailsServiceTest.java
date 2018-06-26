package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.model.CustomUserDetails;
import hr.tvz.java.teambuildingbooking.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomUserDetailsServiceTest {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Test
    public void CustomUserDetailsServiceAutowired() {
        assertNotNull(customUserDetailsService);
    };

    @Test
    public void loadUserByUsername() {
        String username = "user";

        User user = new User();
        user.setUsername(username);
        user.setName("Mate");
        user.setPassword("pass");
        user.setSurname("Matić");
        user.setEmail("mate.mate@mate.ma");
        user.setEnabled(true);

        CustomUserDetails cud = new CustomUserDetails(user);

        assertEquals(cud.getUsername(), customUserDetailsService.loadUserByUsername(username).getUsername());

    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameNotFoundException() {
        String username = "Matica";

        User user = new User();
        user.setUsername("Matica");
        user.setName("Mate");
        user.setPassword("pass");
        user.setSurname("Matić");
        user.setEmail("mate.mate@mate.ma");
        user.setEnabled(true);

        CustomUserDetails cud = new CustomUserDetails(user);

        assertEquals(cud.getUsername(), customUserDetailsService.loadUserByUsername(username).getUsername());

    }

}