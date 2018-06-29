package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.model.Feedback;
import hr.tvz.java.teambuildingbooking.model.form.NewReviewForm;
import hr.tvz.java.teambuildingbooking.service.FeedbackService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedbackServiceImplTest {

    @Autowired
    private FeedbackService feedbackService;

    @Test
    public void FeedbackServiceAutowired() {
        assertNotNull(feedbackService);
    }

    @Test
    public void createFeedback() {
        // arrange ...
        NewReviewForm newReviewForm = new NewReviewForm();
        newReviewForm.setComment("Comment");
        newReviewForm.setNumberOfStars(3);
        newReviewForm.setOfferId(1);

        String username = "user";

        // act ...
        Feedback feedback = feedbackService.createFeedback(newReviewForm, username);

        // assert ...
        assertNotNull(feedback);
        assertEquals(newReviewForm.getComment(), feedback.getComment());
        assertEquals(Integer.valueOf(newReviewForm.getNumberOfStars()), feedback.getNumberOfStars());
        assertEquals(Long.valueOf(newReviewForm.getOfferId()), feedback.getOffer().getId());
    }

    @Test
    public void average() {
        // arrange ...
        Long offerId = new Long(1);

        // act ...
        double averageRating = feedbackService.average(offerId);

        // assert ...
        assertNotNull(averageRating);
    }
}