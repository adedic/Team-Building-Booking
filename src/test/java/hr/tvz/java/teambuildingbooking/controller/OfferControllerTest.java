package hr.tvz.java.teambuildingbooking.controller;

import hr.tvz.java.teambuildingbooking.config.SpringSecurityConfig;
import hr.tvz.java.teambuildingbooking.config.ThymeleafConfig;
import hr.tvz.java.teambuildingbooking.config.WebConfig;
import org.hamcrest.Matchers;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(value = {SpringSecurityConfig.class,
        ThymeleafConfig.class, WebConfig.class})
public class OfferControllerTest {

    private static final String NEW_OFFER_VIEW_NAME = "offer/new-offer";
    private static final String SEARCH_OFFER_VIEW_NAME = "offer/search-offer";
    private static final String MY_OFFERS_VIEW_NAME = "offer/myOffers";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void newOffer() throws Exception {
        this.mockMvc
                .perform(get("/offer/new").with(user("provider").password("secret").roles("PROVIDER")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categories", "newOfferForm"))
                .andExpect(model().attribute("categories", Matchers.hasSize(6)))
                .andExpect(view().name(NEW_OFFER_VIEW_NAME));
    }

    @Test
    public void handleNewOfferForm() {
    }

    @Test
    public void editOffer() {

    }

    @Test
    public void handleEditOfferForm() {
    }

    @Test
    public void searchOffer() throws Exception {
        this.mockMvc
                .perform(get("/offer/search").with(user("user").password("secret").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("topOffers", "offers", "searchOfferForm"))
                .andExpect(model().attribute("topOffers", Matchers.hasSize(5)))
                .andExpect(view().name(SEARCH_OFFER_VIEW_NAME));
    }

    @Test
    public void findSearchResults() {

    }

    @Test
    public void newReview() {

    }

    @Test
    public void showDetails() {

    }

    @Test
    public void updateActivity() {

    }

    @Test
    public void showMyOffers() throws Exception {
        this.mockMvc
                .perform(get("/offer/myOffers").with(user("provider").password("secret").roles("PROVIDER")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("myOffers"))
                .andExpect(model().attribute("myOffers", Matchers.hasSize(5)))
                .andExpect(view().name(MY_OFFERS_VIEW_NAME));
    }

    @Test
    public void addNewOfferFormValidator() {
    }

    @Test
    public void searchOfferFormValidator() {
    }

    @Test
    public void addEditOfferFormValidator() {
    }
}
