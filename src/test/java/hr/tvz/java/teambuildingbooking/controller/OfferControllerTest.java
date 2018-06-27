package hr.tvz.java.teambuildingbooking.controller;

import hr.tvz.java.teambuildingbooking.TeamBuildingBookingApplication;
import hr.tvz.java.teambuildingbooking.constants.ImageBase64String;
import hr.tvz.java.teambuildingbooking.model.form.NewOfferForm;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = TeamBuildingBookingApplication.class)
public class OfferControllerTest {

    // --- view names ---------------------------------------------------------

    private static final String NEW_OFFER_VIEW_NAME = "offer/new-offer";
    private static final String SEARCH_OFFER_VIEW_NAME = "offer/search-offer";
    private static final String DETAILS_VIEW_NAME = "offer/details";
    private static final String EDIT_OFFER_VIEW_NAME = "offer/edit-offer";
    private static final String MY_OFFERS_VIEW_NAME = "offer/myOffers";

    // --- view redirects -----------------------------------------------------

    private static final String OFFER_SEARCH_REDIRECT_NAME = "redirect:/offer/search";
    private static final String OFFER_DETAILS_REDIRECT_NAME = "redirect:/offer/details/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    OfferService offerService;

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
    public void handleNewOfferForm() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "urban.png", MediaType.IMAGE_PNG_VALUE, ImageBase64String.IMAGE_STRING.getBytes());

        mockMvc.perform(multipart("/offer/new").file(file).with(user("provider").password("secret").roles("PROVIDER"))
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .param("categories", "Zabava")
                .param("name", "Nova ponuda")
                .param("country", "Hrvatska")
                .param("city", "Daruvar")
                .param("address", "Daruvarska ulica 18")
                .param("minNumberOfUsers", "1")
                .param("maxNumberOfUsers", "100")
                .param("pricePerPerson", "50")
                .param("availableFrom", "2018-17-11")
                .param("availableTo", "2018-20-11")
                .param("description", "Idi pa vidi")
                .sessionAttr("newOfferForm", new NewOfferForm()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/offer/details/110"));

    }

    @Test
    public void handleNewOfferFormFound() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "urban.png", MediaType.IMAGE_PNG_VALUE, ImageBase64String.IMAGE_STRING.getBytes());

        mockMvc.perform(multipart("/offer/new").file(file).with(user("provider").password("secret").roles("PROVIDER"))
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .param("categories", "")
                .param("name", "")
                .param("country", "")
                .param("city", "")
                .param("address", "")
                .param("minNumberOfUsers", "")
                .param("maxNumberOfUsers", "")
                .param("pricePerPerson", "")
                .param("availableFrom", "")
                .param("availableTo", "")
                .param("description", "")
                .sessionAttr("newOfferForm", new NewOfferForm()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("categories", Matchers.hasSize(6)))
                .andExpect(view().name(NEW_OFFER_VIEW_NAME));

    }

    @Test
    public void editOffer() throws Exception {
        this.mockMvc.perform(get("/offer/edit/1").with(user("provider").password("secret").roles("PROVIDER"))
                        .param("id", "1"))
                        .andExpect(model().attributeExists("editOfferForm"))
                        .andExpect(status().isOk())
                        .andExpect(view().name(EDIT_OFFER_VIEW_NAME));

        assertNotNull(offerService.findOne(1L));
    }

    @Test
    public void editOfferUser() throws Exception {
        this.mockMvc.perform(get("/offer/edit/1000").with(user("provider").password("secret").roles("PROVIDER")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(OFFER_SEARCH_REDIRECT_NAME));

    }

    @Test
    public void handleEditOfferForm() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "urban.png", MediaType.IMAGE_PNG_VALUE, ImageBase64String.IMAGE_STRING.getBytes());

        mockMvc.perform(multipart("/offer/edit").file(file).with(user("provider").password("secret").roles("PROVIDER"))
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .param("id", "1")
                .param("name", "Nova ponuda")
                .param("categories", "Zabava")
                .param("country", "Hrvatska")
                .param("city", "Daruvar")
                .param("address", "Daruvarska ulica 18")
                .param("minNumberOfUsers", "1")
                .param("maxNumberOfUsers", "100")
                .param("pricePerPerson", "50")
                .param("availableFrom", "2018-11-11")
                .param("availableTo", "2018-11-20")
                .param("image", "slikicaa")
                .param("description", "Idi pa vidi")
                .param("dateAdded", "2000-11-20"))
                .andExpect(status().isFound())
                .andExpect(view().name(OFFER_DETAILS_REDIRECT_NAME + "1"));
    }

    @Test
    public void handleEditOfferFormFound() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "urban.png", MediaType.IMAGE_PNG_VALUE, ImageBase64String.IMAGE_STRING.getBytes());

        mockMvc.perform(multipart("/offer/edit").file(file).with(user("provider").password("secret").roles("PROVIDER"))
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .param("id", "")
                .param("name", "")
                .param("categories", "")
                .param("country", "")
                .param("city", "")
                .param("address", "")
                .param("minNumberOfUsers", "")
                .param("maxNumberOfUsers", "")
                .param("pricePerPerson", "")
                .param("availableFrom", "")
                .param("availableTo", "")
                .param("image", "")
                .param("description", "")
                .param("dateAdded", ""))
                .andExpect(status().isOk())
                .andExpect(model().attribute("categories", Matchers.hasSize(6)))
                .andExpect(view().name(EDIT_OFFER_VIEW_NAME));
    }

    @Test
    public void searchOffer() throws Exception {
        this.mockMvc
                .perform(get("/offer/search").with(user("admin").password("secret").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("topOffers", "offers", "searchOfferForm"))
                .andExpect(model().attribute("topOffers", Matchers.hasSize(5)))
                .andExpect(view().name(SEARCH_OFFER_VIEW_NAME));

    }

    //TODO
//    @Test
//    public void findSearchResults() throws Exception {
//        this.mockMvc
//                .perform(post("/offer/search").with(user("admin").password("secret").roles("ADMIN"))
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .sessionAttr("searchOfferForm", new SearchOfferForm())
//                        .param("category", "Sport")
//                        .param("date", "2018-06-29")
//                        .param("numOfPeople", "12")
//                        .param("country", "Hrvatska")
//                        .param("city", "Nacionalni Park Krka"))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("offers", "titleResults"))
//                .andExpect(model().attribute("offers", Matchers.hasSize(1)))
//                .andExpect(view().name(SEARCH_OFFER_VIEW_NAME));
//    }

    @Test
    public void showDetails() throws Exception {
        this.mockMvc.perform(get("/offer/details/1").with(user("user").password("secret").roles("USER"))
                .param("id", "1"))
                .andExpect(model().attributeExists("offer", "reservationForm"))
                .andExpect(status().isOk())
                .andExpect(view().name(DETAILS_VIEW_NAME));

        assertNotNull(offerService.findOne(1L));
    }

    @Test
    public void showDetailsUser() throws Exception {
        this.mockMvc.perform(get("/offer/details/10000").with(user("user").password("secret").roles("USER")))
                .andExpect(status().is3xxRedirection())
        .andExpect(view().name(OFFER_SEARCH_REDIRECT_NAME));
    }

    @Test
    public void updateActivity() throws Exception {
        this.mockMvc.perform(get("/offer/updateActivity/1").with(user("provider").password("secret").roles("PROVIDER"))
                .param("id", "1"))
                .andExpect(model().attributeExists("feedbacks", "reservationForm"))
                .andExpect(status().isOk())
                .andExpect(view().name(DETAILS_VIEW_NAME));

        assertNotNull(offerService.findOne(1L));
    }

    @Test
    public void updateActivityUser() throws Exception {
        this.mockMvc.perform(get("/offer/updateActivity/1000").with(user("user").password("secret").roles("USER")))
                .andExpect(status().is3xxRedirection())
        .andExpect(view().name(OFFER_SEARCH_REDIRECT_NAME));

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
