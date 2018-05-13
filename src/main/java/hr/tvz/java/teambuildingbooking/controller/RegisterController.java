package hr.tvz.java.teambuildingbooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegisterController {

    private static final String REGISTER_VIEW_NAME = "register";

    @RequestMapping("/register")
    private String register(Model model) {
    	model.addAttribute("navLinkText", "Prijava");
    	model.addAttribute("navLink", "/login");
        return REGISTER_VIEW_NAME;
    }
}