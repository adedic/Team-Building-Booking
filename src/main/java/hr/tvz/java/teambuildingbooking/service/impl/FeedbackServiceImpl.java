package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.mapper.FeedbackMapper;
import hr.tvz.java.teambuildingbooking.model.Feedback;
import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.NewReviewForm;
import hr.tvz.java.teambuildingbooking.repository.FeedbackRepository;
import hr.tvz.java.teambuildingbooking.service.FeedbackService;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import hr.tvz.java.teambuildingbooking.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Slf4j
@Service

public class FeedbackServiceImpl implements FeedbackService {
    private UserService userService;
    private FeedbackRepository feedbackRepository;
    private OfferService offerService;
    @Override
    public Feedback createFeedback(NewReviewForm newReviewForm, String username) throws ParseException, IOException {
        Feedback feedback = FeedbackMapper.INSTANCE.newReviewFormToReview(newReviewForm);
        int stars = newReviewForm.getNumberOfStars();
        feedback.setDateSubmitted(new Date());
        feedback.setDateLastEdited(new Date());

        User user = userService.findByUsername(username);

        feedback.setNumberOfStars(stars);

        feedback.setUser(user);

        Feedback savedFeedback = feedbackRepository.save(feedback);
        return savedFeedback;
    }

    @Override
    public double average(long offerId){
        double average;
        int sum=0, count=0;
        Set<Feedback> feedbacks;
        if(offerService.findOne(offerId).isPresent()){
            Offer offer = offerService.findOne(offerId).get();
            feedbacks = offer.getFeedbacks();
            for(Feedback f : feedbacks){
                count++;
                sum+=f.getNumberOfStars();
            }
            average = sum/count;
        }
        else{
            average = 0;
        }


        return average;
    }
}
