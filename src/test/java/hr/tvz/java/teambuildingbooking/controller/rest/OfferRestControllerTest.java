package hr.tvz.java.teambuildingbooking.controller.rest;

import hr.tvz.java.teambuildingbooking.service.OfferService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OfferRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    OfferService offerService;

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/rest/offer").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(5)));

    }

    @Test
    public void editOffer() throws Exception {
        mockMvc.perform(get("/rest/offer/edit/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void editOfferNotFound() throws Exception {
        mockMvc.perform(get("/rest/offer/edit/1000").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void showDetails() throws Exception {
        mockMvc.perform(get("/rest/offer/details/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void showReviews() throws Exception {
        mockMvc.perform(get("/rest/offer/details/1/reviews").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void showReviewsNotFound() throws Exception {
        mockMvc.perform(get("/rest/offer/details/1000/reviews").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void showMyOffers() throws Exception {
        mockMvc.perform(get("/rest/offer/myOffers/provider").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    public void showMyOffersNotFound() throws Exception {
        mockMvc.perform(get("/rest/offer/myOffers/user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findSearchResults() throws Exception {
        mockMvc.perform(post("/rest/offer/search")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"category\" : \"Hrana i piće\",\n" +
                        "\t\"numOfPeople\" : \"6\",\n" +
                        "\t\"country\" : \"\",\n" +
                        "\t\"city\" : \"\",\n" +
                        "\t\"date\" : \"\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }

    @Test
    public void findSearchResultsNotFound() throws Exception {
        mockMvc.perform(post("/rest/offer/search")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"category\" : \"Hrana i piće\",\n" +
                        "\t\"numOfPeople\" : \"200\",\n" +
                        "\t\"country\" : \"\",\n" +
                        "\t\"city\" : \"\",\n" +
                        "\t\"date\" : \"\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void handleNewOfferForm() throws Exception {
        mockMvc.perform(post("/rest/offer/new")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"editOfferForm\" : {\n" +
                        "\t\t\"name\" : \"Hrana i piće\",\n" +
                        "\t\t\"address\" : \"6\",\n" +
                        "\t\t\"country\" : \"Hrvatska\",\n" +
                        "\t\t\"city\" : \"Zagreb\",\n" +
                        "\t\t\"description\" : \"Opisic\",\n" +
                        "\t\t\"availableFrom\" : \"2018-06-30\",\n" +
                        "\t\t\"availableTo\" : \"2018-07-15\",\n" +
                        "\t\t\"minNumberOfUsers\" : 10,\n" +
                        "\t\t\"maxNumberOfUsers\" : 20,\n" +
                        "\t\t\"pricePerPerson\" : 200,\n" +
                        "\t\t\"categories\" : []\n" +
                        "\t},\n" +
                        "\t\"base64string\" : \"lalalal\",\n" +
                        "\t\"fileName\" : \"slikica\",\n" +
                        "\t\"fileSize\" : 102,\n" +
                        "\t\"usename\" : \"provider\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void handleNewOfferFormNotFound() throws Exception {
        mockMvc.perform(post("/rest/offer/new")
                .accept(MediaType.APPLICATION_JSON)
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void handleEditOfferForm() throws Exception {
        mockMvc.perform(post("/rest/offer/edit")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"editOfferForm\" : {\n" +
                        "\t\t\"id\" : 5,\n" +
                        "\t\t\"name\" : \"Hrana i piće\",\n" +
                        "\t\t\"address\" : \"6\",\n" +
                        "\t\t\"country\" : \"Hrvatska\",\n" +
                        "\t\t\"city\" : \"Zagreb\",\n" +
                        "\t\t\"image\" : \"ZagrebZagreb\",\n" +
                        "\t\t\"description\" : \"Opisic\",\n" +
                        "\t\t\"dateAdded\" : \"2018-06-30\",\n" +
                        "\t\t\"availableFrom\" : \"2018-06-30\",\n" +
                        "\t\t\"availableTo\" : \"2018-07-15\",\n" +
                        "\t\t\"minNumberOfUsers\" : 10,\n" +
                        "\t\t\"maxNumberOfUsers\" : 20,\n" +
                        "\t\t\"pricePerPerson\" : 200,\n" +
                        "\t\t\"categories\" : []\n" +
                        "\t},\n" +
                        "\t\"base64string\" : \"lalalal\",\n" +
                        "\t\"fileName\" : \"slikica\",\n" +
                        "\t\"fileSize\" : 102,\n" +
                        "\t\"usename\" : \"provider\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void handleNewReviewForm() throws Exception {
        mockMvc.perform(post("/rest/offer/newReview")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"newReviewForm\" : {\n" +
                        "    \t\"offerId\": 9,\n" +
                        "\t    \"comment\": \"Komentiram ponudu, baš je super!\",\n" +
                        "\t    \"numberOfStars\": 5\n" +
                        "    },\n" +
                        "    \"username\" : \"user\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

}