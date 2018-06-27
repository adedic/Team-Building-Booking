package hr.tvz.java.teambuildingbooking.controller.rest;

import hr.tvz.java.teambuildingbooking.model.Feedback;
import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.SearchOfferForm;
import hr.tvz.java.teambuildingbooking.model.rest.RestEditOffer;
import hr.tvz.java.teambuildingbooking.model.rest.RestNewOffer;
import hr.tvz.java.teambuildingbooking.model.rest.RestNewReview;
import hr.tvz.java.teambuildingbooking.service.FeedbackService;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import hr.tvz.java.teambuildingbooking.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(path = "rest/offer", produces = "application/json")
@CrossOrigin(origins = "*")
public class OfferRestController {

    private OfferService offerService;
    private UserService userService;
    private FeedbackService feedbackService;

    @Autowired
    public OfferRestController(OfferService offerService, UserService userService, FeedbackService feedbackService) {
        this.offerService = offerService;
        this.userService = userService;
        this.feedbackService = feedbackService;
    }

    @GetMapping
    private ResponseEntity<List<Offer>> findAll() {

        List<Offer> offers = offerService.findAll();

        if (offers.isEmpty()) {
            return new ResponseEntity<>(offers, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @PostMapping("/search")
    private ResponseEntity<List<Offer>> findSearchResults(@RequestBody SearchOfferForm searchOfferForm) {

        List<Offer> offerResults = offerService.findOffers(searchOfferForm);

        if (offerResults.isEmpty()) {
            return new ResponseEntity<>(offerResults, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(offerResults, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Offer> handleNewOfferForm(@RequestBody RestNewOffer restNewOffer) {

        Offer offer = null;
        try {
            offer = offerService.createOffer(restNewOffer.getEditOfferForm(), restNewOffer.getBase64string(), restNewOffer.getFileName(), restNewOffer.getFileSize(), restNewOffer.getUsername());
        } catch (ParseException e) {
            log.info(e.getMessage());
        }

        if (offer != null) {
            return new ResponseEntity<>(offer, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(offer, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/edit/{id}")
    private ResponseEntity<Offer> editOffer(@PathVariable Long id) {
        return getOfferDetails(id);
    }

    @PostMapping("/edit")
    public ResponseEntity<Offer> handleEditOfferForm(@RequestBody RestEditOffer restEditOffer) {

        Offer offer = null;
        try {
            offer = offerService.editOffer(restEditOffer.getEditOfferForm(), restEditOffer.getBase64string(), restEditOffer.getFileName(), restEditOffer.getFileSize(), restEditOffer.getUsername());
        } catch (ParseException e) {
            log.info(e.getMessage());
        }
        if (offer != null) {
            return new ResponseEntity<>(offer, HttpStatus.OK);
        }

        return new ResponseEntity<>(offer, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/details/{id}")
    private ResponseEntity<Offer> showDetails(@PathVariable Long id) {
        return getOfferDetails(id);
    }

    private ResponseEntity<Offer> getOfferDetails(@PathVariable Long id) {
        Optional<Offer> offer = offerService.findOne(id);

        if (offer.isPresent()) {
            return new ResponseEntity<>(offer.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(new Offer(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/details/{id}/reviews")
    private ResponseEntity<Set<Feedback>> showReviews(@PathVariable Long id) {
        Optional<Offer> offer = offerService.findOne(id);

        if (offer.isPresent()) {
            return new ResponseEntity<>(offer.get().getFeedbacks(), HttpStatus.OK);
        }
        Set<Feedback> feedbacks = new HashSet<>();
        return new ResponseEntity<>(feedbacks, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/myOffers/{username}")
    private ResponseEntity<List<Offer>> showMyOffers(@PathVariable String username) {

        List<Offer> offers = new ArrayList<>();

        User currentUser = userService.findByUsername(username);

        if (currentUser != null) {
            offers = offerService.findOffersByUserOrderByDateAdded(currentUser);

            if (!offers.isEmpty()) {
                return new ResponseEntity<>(offers, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(offers, HttpStatus.NOT_FOUND);

    }

    @PostMapping("/newReview")
    private ResponseEntity<Feedback> handleNewReviewForm(@RequestBody RestNewReview review) throws ParseException, IOException{

        Feedback feedback = feedbackService.createFeedback(review.getNewReviewForm(), review.getUsername());

        if (feedback != null) {
            return new ResponseEntity<>(feedback, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(feedback, HttpStatus.NOT_FOUND);
    }

}
