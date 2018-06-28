package hr.tvz.java.teambuildingbooking.controller;

import hr.tvz.java.teambuildingbooking.model.Feedback;
import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.form.NewReviewForm;
import hr.tvz.java.teambuildingbooking.service.FeedbackService;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/review")
@SessionAttributes({"offers", "searchOfferForm", "categories", "topOffers"})
public class FeedbackController {

    //view names
    private static final String REVIEWS_VIEW_NAME = "offer/reviews";
    private static final String NEW_REVIEW_VIEW_NAME = "offer/new-review";

    //view redirects
    private static final String OFFER_SEARCH_REDIRECT_NAME = "redirect:/offer/search";

    //model attribute names
    private static final String OFFER_NOT_FOUND_REDIRECT_ATTRIBUTE = "offerNotFound";

    //autowired components
    private final OfferService offerService;
    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(OfferService offerService, FeedbackService feedbackService){
        this.offerService = offerService;
        this.feedbackService = feedbackService;
    }

    @RequestMapping("/details/{id}/reviews")
    private String showReviews(Model model, @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Offer> offer = offerService.findOne(id);
        if (offer.isPresent()) {
            model.addAttribute("feedbacks", offer.get().getFeedbacks());
            log.info("---> Fetching offer entity with ID = " + id + " and all its children from the database ...");
        } else {
            redirectAttributes.addFlashAttribute(OFFER_NOT_FOUND_REDIRECT_ATTRIBUTE, "Ponuda s ID = " + id + " nije pronađena!");
            return OFFER_SEARCH_REDIRECT_NAME;
        }

        return REVIEWS_VIEW_NAME;
    }

    @Secured({"USER, ADMIN"})
    @RequestMapping("/newReview/{id}")
    private String newReview(Model model, @PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        Optional<Offer> offer = offerService.findOne(id);
        if(offer.isPresent()){
            model.addAttribute("newReviewForm", new NewReviewForm());
            model.addAttribute("offer", offer.get());
            return NEW_REVIEW_VIEW_NAME;
        }
        else{
            redirectAttributes.addFlashAttribute(OFFER_NOT_FOUND_REDIRECT_ATTRIBUTE, "Ponuda s ID = " + id + "nije pronađena!");
            return OFFER_SEARCH_REDIRECT_NAME;
        }
    }

    @Secured({"USER, ADMIN"})
    @PostMapping("/newReview")
    private String handleNewReviewForm(@Valid @ModelAttribute("newReviewForm") NewReviewForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal, Model model) throws ParseException, IOException {
        if(bindingResult.hasErrors()){
            return NEW_REVIEW_VIEW_NAME;
        }
        Feedback feedback = feedbackService.createFeedback(form, principal.getName());
        //redirectAttributes.addFlashAttribute("createSuccess", "Dodavanje osvrta je uspjelo.!"); uspjesno dodavanje


        return "redirect:/offer/details/" + feedback.getOffer().getId();
    }
}
