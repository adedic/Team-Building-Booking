package hr.tvz.java.teambuildingbooking.controller;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.form.NewOfferForm;
import hr.tvz.java.teambuildingbooking.service.CategoryService;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Optional;


@Controller
@RequestMapping("/offer")
public class OfferController {

    private static final String NEW_OFFER_VIEW_NAME = "offer/new-offer";
    private static final String SEARCH_OFFER_VIEW_NAME = "offer/search-offer";
    private static final String SEARCH_RESULTS_VIEW_NAME = "offer/search-results";
    private static final String DETAILS_VIEW_NAME = "offer/details";
    private static final String REVIEWS_VIEW_NAME = "offer/reviews";


    @Autowired
    private OfferService offerService;

    @Autowired
    private CategoryService categoryService;

    @Secured("PROVIDER")
    @RequestMapping("/new")
    private String newOffer(Model model) {
        return NEW_OFFER_VIEW_NAME;
    }

    @PostMapping("/new")
    public String handleRegistrationForm(@Valid @ModelAttribute("newOfferForm") NewOfferForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws ParseException {
        if (bindingResult.hasErrors()) {
            return NEW_OFFER_VIEW_NAME;
        }
        offerService.createOffer(form);
        redirectAttributes.addFlashAttribute("createSuccess", "Dodavanje nove ponude je uspjelo");

        return "redirect:/search";
    }


    @RequestMapping("/search")
    private String searchOffer(Model model) {
        model.addAttribute("categories", categoryService.findAll());
//        model.addAttribute("topOffers", offerService.findTopOffers());
        model.addAttribute("topOffers", offerService.findAll());
        return SEARCH_OFFER_VIEW_NAME;
    }

    @PostMapping("/searchOffer")
    private String findSearchResults(Model model){
        model.addAttribute("offers", offerService.findAll());
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
        if(offer.isPresent()){
            model.addAttribute("offer", offer.get());
        }

        return DETAILS_VIEW_NAME;
    }

    @RequestMapping("/details/{id}/reviews")
    private ModelAndView showReviews(Model model, @PathVariable("id") Long id) {
        Optional<Offer> offer = offerService.findOne(id);
        if(offer.isPresent()){
            model.addAttribute("feedbacks", offer.get().getFeedbacks());
        }

        return new ModelAndView(REVIEWS_VIEW_NAME);
    }
}
