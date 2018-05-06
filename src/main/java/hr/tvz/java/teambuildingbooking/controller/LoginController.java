package hr.tvz.java.teambuildingbooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mstuban on 06.05.2018.
 */
@Controller
public class LoginController {

    private final static String LOGIN_VIEW_NAME = "login";

    @RequestMapping("/login")
    private String login() {
        return LOGIN_VIEW_NAME;
    }
}
