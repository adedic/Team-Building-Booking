package hr.tvz.java.teambuildingbooking.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public class IndexController {

    private static final String INDEX_VIEW_NAME = "index";

    @GetMapping
    public String home(Model model){
        return INDEX_VIEW_NAME;
    }
}
