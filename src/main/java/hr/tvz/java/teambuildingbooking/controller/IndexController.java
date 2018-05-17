package hr.tvz.java.teambuildingbooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * `
 * Created by mstuban on 06.05.2018.
 */
@Controller
public class IndexController {

    private static final String INDEX_VIEW_NAME = "offer/search-offer";

    @GetMapping({"/", "index"})
    public String index(Model model) {
        return INDEX_VIEW_NAME;
    }
}
