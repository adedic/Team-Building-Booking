package hr.tvz.java.teambuildingbooking.controller;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/offer")
public class OfferController {

    private static final String NEW_OFFER_VIEW_NAME = "offer/new-offer";
    private static final String SEARCH_OFFER_VIEW_NAME = "offer/search-offer";
    private static final String SEARCH_RESULTS_VIEW_NAME = "offer/search-results";
    private static final String DETAILS_VIEW_NAME = "offer/details";

    @Autowired
    private OfferService offerService;

    @Secured("PROVIDER")
    @RequestMapping("/new")
    private String newOffer(Model model) {
        return NEW_OFFER_VIEW_NAME;
    }

    @RequestMapping("/search")
    private String searchOffer(Model model) {
        return SEARCH_OFFER_VIEW_NAME;
    }

    @RequestMapping("/results")
    private String showResults(Model model) {
        List<Offer> offers = offerService.findAll();
        return SEARCH_RESULTS_VIEW_NAME; }

    @RequestMapping("/details")
    private String showDetails(Model model) { return DETAILS_VIEW_NAME; }
}
