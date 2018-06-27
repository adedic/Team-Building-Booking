package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.model.form.NewReviewForm;
import hr.tvz.java.teambuildingbooking.service.FeedbackService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedbackServiceImplTest {

    @Autowired
    FeedbackService feedbackService;

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

        String username = "user";
        assertNotNull(feedbackService.createFeedback(newReviewForm, username));
}

    @Test
    public void average() {
        Long offerId = new Long(1);
        assertNotNull(feedbackService.average(offerId));
    }
}