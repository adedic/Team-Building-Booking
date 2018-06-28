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
public class RegistrerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void register() throws Exception {
        mockMvc.perform(post("/rest/register")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"userRole\" : \"ROLE_ADMIN\",\n" +
                        "\t\"name\" : \"namename\",\n" +
                        "\t\"surname\" : \"surname\",\n" +
                        "\t\"dateOfBirth\" : \"1212-12-12\",\n" +
                        "\t\"email\" : \"mail\",\n" +
                        "\t\"telephone\" : \"12345\",\n" +
                        "\t\"username\" : \"usrname\",\n" +
                        "\t\"password\" : \"pass\",\n" +
                        "\t\"confirmPassword\" : \"pass\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void registerBad() throws Exception {
        mockMvc.perform(post("/rest/register")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"userRole\" : \"ROLE_ADMIN\",\n" +
                        "\t\"name\" : \"namenameusername\",\n" +
                        "\t\"surname\" : \"surname\",\n" +
                        "\t\"dateOfBirth\" : \"1212-\",\n" +
                        "\t\"email\" : \"mail\",\n" +
                        "\t\"telephone\" : \"12345\",\n" +
                        "\t\"username\" : \"usrname\",\n" +
                        "\t\"password\" : \"pass\",\n" +
                        "\t\"confirmPassword\" : \"pass\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}