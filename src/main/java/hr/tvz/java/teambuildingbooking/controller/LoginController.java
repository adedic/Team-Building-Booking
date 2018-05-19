package hr.tvz.java.teambuildingbooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by mstuban on 06.05.2018.
 */
@Controller
public class LoginController {

    private static final String LOGIN_VIEW_NAME = "login";

    @RequestMapping("/login")
    private String login(Model model) {
        model.addAttribute("navLinkText", "Registracija");
        return LOGIN_VIEW_NAME;
    }

    @RequestMapping("/logut")
    private String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/login?editProfileSuccess";
    }
}
