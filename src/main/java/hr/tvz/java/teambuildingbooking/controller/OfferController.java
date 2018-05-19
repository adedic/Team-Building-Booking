package hr.tvz.java.teambuildingbooking.controller;

import hr.tvz.java.teambuildingbooking.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offer")
public class OfferController {

    private static final String NEW_OFFER_VIEW_NAME = "offer/new-offer";
    private static final String SEARCH_OFFER_VIEW_NAME = "offer/search-offer";
    private static final String SEARCH_RESULTS_VIEW_NAME = "offer/search-results";

    @Autowired
    private CategoryService categoryService;

    @Secured("PROVIDER")
    @RequestMapping("/new")
    private String newOffer(Model model) {
        return NEW_OFFER_VIEW_NAME;
    }

    @RequestMapping("/search")
    private String searchOffer(Model model) {
        model.addAttribute("categories", categoryService.fetchCategories());
        return SEARCH_OFFER_VIEW_NAME;
    }

    @RequestMapping("/results")
    private String showResults(Model model) {
        return SEARCH_RESULTS_VIEW_NAME;
    }
}
