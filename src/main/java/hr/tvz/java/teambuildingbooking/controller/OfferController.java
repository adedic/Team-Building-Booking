package hr.tvz.java.teambuildingbooking.controller;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.form.SearchOfferForm;
import hr.tvz.java.teambuildingbooking.service.CategoryService;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import hr.tvz.java.teambuildingbooking.validator.SearchOfferFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Optional;


@Controller
@RequestMapping("/offer")
@SessionAttributes("searchOfferForm")
public class OfferController {

    private static final String NEW_OFFER_VIEW_NAME = "offer/new-offer";
    private static final String SEARCH_OFFER_VIEW_NAME = "offer/search-offer";
    private static final String SEARCH_RESULTS_VIEW_NAME = "offer/search-results";
    private static final String DETAILS_VIEW_NAME = "offer/details";
    private static final String REVIEWS_VIEW_NAME = "offer/reviews";

    private OfferService offerService;

    private CategoryService categoryService;

    private SearchOfferFormValidator searchOfferFormValidator;

    @Autowired
    public OfferController(OfferService offerService, CategoryService categoryService, SearchOfferFormValidator searchOfferFormValidator) {
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.searchOfferFormValidator = searchOfferFormValidator;
    }

    @Secured("PROVIDER")
    @RequestMapping("/new")
    private String newOffer(Model model) {
        return NEW_OFFER_VIEW_NAME;
    }

    @GetMapping("/search")
    private String searchOffer(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("topOffers", offerService.findTopOffers());
        model.addAttribute("searchOfferForm", new SearchOfferForm());
        return SEARCH_OFFER_VIEW_NAME;
    }

    @PostMapping("/search")
    private String findSearchResults(@Valid @ModelAttribute("searchOfferForm") SearchOfferForm searchOfferForm, Model model, BindingResult bindingResult) throws ParseException {

        if(bindingResult.hasErrors()) {
            return SEARCH_OFFER_VIEW_NAME;
        }

        model.addAttribute("offers", offerService.findOffers(searchOfferForm));
        return SEARCH_RESULTS_VIEW_NAME;
    }

    @RequestMapping("/results")
    private String showResults(Model model) {

        model.addAttribute("offers", offerService.findAll());
        return SEARCH_RESULTS_VIEW_NAME;
    }

    @RequestMapping("/details/{id}")
    private String showDetails(Model model, @PathVariable("id") Long id) {
        Optional<Offer> offer = offerService.findOne(id);
        if (offer.isPresent()) {
            model.addAttribute("offer", offer.get());
        }

        return DETAILS_VIEW_NAME;
    }

    @RequestMapping("/details/{id}/reviews")
    private ModelAndView showReviews(Model model, @PathVariable("id") Long id) {
        Optional<Offer> offer = offerService.findOne(id);
        if (offer.isPresent()) {
            model.addAttribute("feedbacks", offer.get().getFeedbacks());
        }

        return new ModelAndView(REVIEWS_VIEW_NAME);
    }

    @InitBinder("searchOfferForm")
    public void searchOfferFormValidator(WebDataBinder dataBinder) {
        dataBinder.addValidators(searchOfferFormValidator);
    }


}
