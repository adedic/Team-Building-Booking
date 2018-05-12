package hr.tvz.java.teambuildingbooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offer")
public class OfferController {

    private static final String NEW_OFFER_VIEW_NAME = "new-offer";
    private final static String SEARCH_OFFER_VIEW_NAME = "offer/search-offer";

    @RequestMapping("/new")
    private String newOffer(Model model) {
        return NEW_OFFER_VIEW_NAME;
    }

    @RequestMapping("/search")
    private String searchOffer(Model model) {
        return SEARCH_OFFER_VIEW_NAME;
    }
}
