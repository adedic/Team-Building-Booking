package hr.tvz.java.teambuildingbooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SearchOfferController {

    private final static String SEARCH_OFFER_VIEW_NAME = "searchOffer";

    @RequestMapping("/searchOffer")
    private String login(Model model) {
        return SEARCH_OFFER_VIEW_NAME;
    }
}
