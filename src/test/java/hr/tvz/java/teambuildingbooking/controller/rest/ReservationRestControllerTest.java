package hr.tvz.java.teambuildingbooking.controller.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReservationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void saveReservation() throws Exception {
        mockMvc.perform(post("/rest/reservation/saveReservation")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"reservationForm\" : {\n" +
                        "\t\t\"offerId\" : 1,\n" +
                        "\t\t\"date\" : \"2018-06-30\",\n" +
                        "\t\t\"dateString\" : \"2018-06-30\",\n" +
                        "\t\t\"numberOfUsers\" : 10\n" +
                        "\t},\n" +
                        "\t\"username\" : \"user\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void saveReservationBad() throws Exception {
        mockMvc.perform(post("/rest/reservation/saveReservation")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"reservationForm\" : {\n" +
                        "\t\t\"offerId\" : 1,\n" +
                        "\t\t\"date\" : \"2018-06-18\",\n" +
                        "\t\t\"dateString\" : \"2018-06-18\",\n" +
                        "\t\t\"numberOfUsers\" : 10\n" +
                        "\t},\n" +
                        "\t\"username\" : \"user\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}