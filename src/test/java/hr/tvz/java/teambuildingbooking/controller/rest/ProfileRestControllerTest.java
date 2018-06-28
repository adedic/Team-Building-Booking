package hr.tvz.java.teambuildingbooking.controller.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProfileRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/rest/profile").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void getMyProfile() throws Exception {
        mockMvc.perform(get("/rest/profile/me/user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.username", is("user")));
    }

    @Test
    public void getMyProfileNotFound() throws Exception {
        mockMvc.perform(get("/rest/profile/me/username").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getUserProfile() throws Exception {
        mockMvc.perform(get("/rest/profile/user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.username", is("user")));
    }

    @Test
    public void getEditProfile() throws Exception {
        mockMvc.perform(get("/rest/profile/edit/user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.username", is("user")));
    }

    @Test
    public void editProfile() throws Exception {
        mockMvc.perform(put("/rest/profile/edit")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": 2,\n" +
                        "    \"name\": \"ProviderkoUpdate\",\n" +
                        "    \"surname\": \"Provideric\",\n" +
                        "    \"dateOfBirth\": \"2015-09-05T22:00:00.000+0000\",\n" +
                        "    \"email\": \"provider@booking.com\",\n" +
                        "    \"telephone\": \"23232323\",\n" +
                        "    \"username\": \"provider\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void editProfileBad() throws Exception {
        mockMvc.perform(put("/rest/profile/edit")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": 2,\n" +
                        "    \"name\": \"ProviderkoUpdate\",\n" +
                        "    \"surname\": \"Provideric\",\n" +
                        "    \"dateOfBirth\": \"2015-\",\n" +
                        "    \"email\": \"provider@booking.com\",\n" +
                        "    \"telephone\": \"23232323\",\n" +
                        "    \"username\": \"provider\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }



}