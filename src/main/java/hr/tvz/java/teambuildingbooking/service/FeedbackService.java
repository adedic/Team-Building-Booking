package hr.tvz.java.teambuildingbooking.service;

import hr.tvz.java.teambuildingbooking.model.Feedback;
import hr.tvz.java.teambuildingbooking.model.form.NewReviewForm;

import java.io.IOException;
import java.text.ParseException;

public interface FeedbackService {
    Feedback createFeedback(NewReviewForm newReviewForm, String username);

    double average(long offerId);
}
