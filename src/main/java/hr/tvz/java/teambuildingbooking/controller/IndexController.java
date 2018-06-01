package hr.tvz.java.teambuildingbooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * Created by mstuban on 06.05.2018.
 */
@Controller
public class IndexController {

    private static final String OFFER_SEARCH_REDIRECT = "redirect:/offer/search";
    private static final String ABOUT_VIEW_NAME = "about";
    private static final String CONTACT_VIEW_NAME = "contact";

    @GetMapping({"/", "index"})
    public String index(Model model) {
        return OFFER_SEARCH_REDIRECT;
    }

    @GetMapping({"/about"})
    public String about() {
        return ABOUT_VIEW_NAME;
    }

    @GetMapping({"/contact"})
    public String contact(){
        return CONTACT_VIEW_NAME;
    }
}
