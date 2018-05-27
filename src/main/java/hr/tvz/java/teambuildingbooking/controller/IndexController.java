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

    @GetMapping({"/", "index"})
    public String index(Model model) {
        return OFFER_SEARCH_REDIRECT;
    }
}
