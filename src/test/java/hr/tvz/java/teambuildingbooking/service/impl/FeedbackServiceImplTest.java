package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.mapper.FeedbackMapper;
import hr.tvz.java.teambuildingbooking.model.Feedback;
import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.NewReviewForm;
import hr.tvz.java.teambuildingbooking.service.FeedbackService;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import hr.tvz.java.teambuildingbooking.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedbackServiceImplTest {

    @MockBean
    FeedbackService feedbackService;

    @MockBean
    UserService userService;

    @Test
    public void FeedbackServiceAutowired() {
        assertNotNull(feedbackService);
    };

    @Test
    public void createFeedback() {
        NewReviewForm newReviewForm = new NewReviewForm();
        newReviewForm.setComment("Comment");
        newReviewForm.setNumberOfStars(3);
        newReviewForm.setOfferId(1);

        Feedback feedback = FeedbackMapper.INSTANCE.newReviewFormToReview(newReviewForm);
        feedback.setDateSubmitted(new Date());
        feedback.setDateLastEdited(new Date());

        User user = new User();
        String username = "user";
        user.setUsername(username);
        user.setName("Mate");
        user.setPassword("pass");
        user.setSurname("Matić");
        user.setEmail("mate.mate@mate.ma");
        user.setEnabled(true);

        feedback.setUser(user);

        Mockito.when(feedbackService.createFeedback(newReviewForm, username)).thenReturn(feedback);
    }

    @Test
    public void average() {

        Set<Feedback> feedbacks = new HashSet<>();

        Long offerId = new Long(1);

        Offer offer = new Offer();
        offer.setName("naziv");
        offer.setId(new Long(1));

        Feedback feedback1 = new Feedback();
        feedback1.setUser(new User());
        feedback1.setComment("comm");
        feedback1.setOffer(new Offer());
        feedback1.setNumberOfStars(5);

        Feedback feedback2 = new Feedback();
        feedback2.setUser(new User());
        feedback2.setComment("comm");
        feedback2.setOffer(new Offer());
        feedback2.setNumberOfStars(4);

        feedbacks.add(feedback1);
        feedbacks.add(feedback2);

        offer.setFeedbacks(feedbacks);

        OptionalDouble avg = feedbacks
                .stream()
                .mapToDouble(feedback -> feedback.getNumberOfStars())
                .average();

        Mockito.when(feedbackService.average(offerId)).thenReturn(avg.orElse(0));

    }
}