package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.model.CustomUserDetails;
import hr.tvz.java.teambuildingbooking.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomUserDetailsServiceTest {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Test
    public void CustomUserDetailsServiceAutowired() {
        assertNotNull(customUserDetailsService);
    }

    @Test
    public void loadUserByUsername_WhenUsernameExists() {
        // arrange ...
        String username = "user";

        User user = new User();
        user.setUsername(username);
        user.setName("Mate");
        user.setPassword("pass");
        user.setSurname("Matić");
        user.setEmail("mate.mate@mate.ma");
        user.setEnabled(true);

        CustomUserDetails cud = new CustomUserDetails(user);

        // act ...
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        // assert ...
        assertNotNull(userDetails);
        assertEquals(cud.getUsername(), userDetails.getUsername());

    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_WhenUsernameDoesNotExist(){
        // arrange ...
        String username = "Matica";

        User user = new User();
        user.setUsername("Matica");
        user.setName("Mate");
        user.setPassword("pass");
        user.setSurname("Matić");
        user.setEmail("mate.mate@mate.ma");
        user.setEnabled(true);

        CustomUserDetails cud = new CustomUserDetails(user);

        // act ...
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        // assert ...
        assertNotNull(username);
        assertNotEquals(cud.getUsername(), userDetails.getUsername());
    }

}