package hr.tvz.java.teambuildingbooking.controller;
import hr.tvz.java.teambuildingbooking.config.SpringSecurityConfig;
import hr.tvz.java.teambuildingbooking.config.ThymeleafConfig;
import hr.tvz.java.teambuildingbooking.config.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(value = {SpringSecurityConfig.class,
        ThymeleafConfig.class, WebConfig.class})
public class LoginControllerTest {
    private static final String LOGIN_VIEW_NAME = "login";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void login() throws Exception{
        this.mockMvc
                .perform(get("/login").with(user("user").password("secret").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name(LOGIN_VIEW_NAME));
    }

    @Test
    public void logout() throws Exception{
        this.mockMvc
                .perform(get("/logout").with(user("user").password("secret").roles("USER")))
                .andExpect(status().isFound());
    }
}
