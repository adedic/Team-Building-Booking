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
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service

public class FeedbackServiceImpl implements FeedbackService {

    private UserService userService;

    private FeedbackRepository feedbackRepository;

    private OfferService offerService;

    public FeedbackServiceImpl(UserService userService, FeedbackRepository feedbackRepository, OfferService offerService) {
        this.userService = userService;
        this.feedbackRepository = feedbackRepository;
        this.offerService = offerService;
    }

    @Override
    public Feedback createFeedback(NewReviewForm newReviewForm, String username) throws ParseException, IOException {
        Feedback feedback = FeedbackMapper.INSTANCE.newReviewFormToReview(newReviewForm);

        long offerId = newReviewForm.getOfferId();
        Optional<Offer> offer = offerService.findOne(offerId);
        if(offer.isPresent()) {
            feedback.setOffer(offer.get());
        }
        feedback.setDateSubmitted(new Date());
        feedback.setDateLastEdited(new Date());

        User user = userService.findByUsername(username);


        feedback.setUser(user);

       return feedbackRepository.save(feedback);
    }

    @Override
    public double average(long offerId) {
        double average = 0;
        int sum = 0;
        int count = 0;
        Set<Feedback> feedbacks;
        if (offerService.findOne(offerId).isPresent()) {
            Offer offer = null;
            Optional<Offer> optionalOffer = offerService.findOne(offerId);
            if(optionalOffer.isPresent()){
                offer = optionalOffer.get();
                feedbacks = offer.getFeedbacks();

                for (Feedback f : feedbacks) {
                    count++;
                    sum += f.getNumberOfStars();
                }
            }

            if(count != 0) {
                average = (double)sum / count;
            }
        }
        return average;
    }
}
