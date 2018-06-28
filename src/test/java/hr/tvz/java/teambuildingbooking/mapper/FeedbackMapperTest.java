package hr.tvz.java.teambuildingbooking.mapper;

import hr.tvz.java.teambuildingbooking.model.form.NewReviewForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedbackMapperTest {

    @Test
    public void newReviewFormToReview() {
        NewReviewForm newReviewForm = new NewReviewForm();
        newReviewForm.setOfferId(1);
        newReviewForm.setNumberOfStars(3);
        newReviewForm.setComment("bla");
        assertNotNull(FeedbackMapper.INSTANCE.newReviewFormToReview(newReviewForm));
    }
}