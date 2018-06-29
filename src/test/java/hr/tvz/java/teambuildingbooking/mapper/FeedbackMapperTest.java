package hr.tvz.java.teambuildingbooking.mapper;

import hr.tvz.java.teambuildingbooking.model.Feedback;
import hr.tvz.java.teambuildingbooking.model.form.NewReviewForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedbackMapperTest {

    @Test
    public void newReviewFormToReview() {
        // arrange ...
        NewReviewForm newReviewForm = new NewReviewForm();
        newReviewForm.setOfferId(1);
        newReviewForm.setNumberOfStars(3);
        newReviewForm.setComment("bla");

        // act ...
        Feedback feedback = FeedbackMapper.INSTANCE.newReviewFormToReview(newReviewForm);

        // assert ...
        assertNotNull(feedback);
        assertEquals(Integer.valueOf(newReviewForm.getNumberOfStars()), feedback.getNumberOfStars());
        assertEquals(newReviewForm.getComment(), feedback.getComment());
    }
}