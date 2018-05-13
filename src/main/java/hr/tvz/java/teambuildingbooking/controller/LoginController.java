package hr.tvz.java.teambuildingbooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mstuban on 06.05.2018.
 */
@Controller
public class LoginController {

    private static final String LOGIN_VIEW_NAME = "login";

    @RequestMapping("/login")
    private String login(Model model) {
    	model.addAttribute("navLinkText", "Registracija");
    	model.addAttribute("navLink", "/register");
        return LOGIN_VIEW_NAME;
    }
}
