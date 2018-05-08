package hr.tvz.java.teambuildingbooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegisterController {

    private final static String REGISTER_VIEW_NAME = "register";

    @RequestMapping("/register")
    private String login(Model model) {
        return REGISTER_VIEW_NAME;
    }
}
