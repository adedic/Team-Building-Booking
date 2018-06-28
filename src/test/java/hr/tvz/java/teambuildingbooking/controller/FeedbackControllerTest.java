package hr.tvz.java.teambuildingbooking.controller;

import hr.tvz.java.teambuildingbooking.config.SpringSecurityConfig;
import hr.tvz.java.teambuildingbooking.config.ThymeleafConfig;
import hr.tvz.java.teambuildingbooking.config.WebConfig;
import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.service.FeedbackService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(value = {SpringSecurityConfig.class,
        ThymeleafConfig.class, WebConfig.class})
public class FeedbackControllerTest {
    private static final String NEW_REVIEW_VIEW_NAME = "offer/new-review";
    private static final String OFFER_SEARCH_REDIRECT_NAME = "/offer/search-offer";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FeedbackService feedbackService;

    @Test
    public void newReview() throws Exception{
        //treba doraditi
        this.mockMvc
                .perform(get("/review/newReview/1").with(user("user").password("secret").roles("USER"))
                        .param("offerId", "1"))
                .andExpect(model().attributeExists("newReviewForm"))
                .andExpect(status().isOk())
                .andExpect(view().name(NEW_REVIEW_VIEW_NAME));
    }

    @Test
    public void handleNewReviewForm()throws Exception{
        this.mockMvc
                .perform(get("/review/newReview/1").with(user("user").password("secret").roles("USER"))
                        .param("offerId", "1")
                        .param("id", "1")
                        .param("numberOfStars", "3")
                        .param("comment", "comment"))
                .andExpect(status().isOk())
                .andExpect(view().name(NEW_REVIEW_VIEW_NAME));
    }
}

