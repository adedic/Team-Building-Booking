package hr.tvz.java.teambuildingbooking.controller;

import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/profile")
public class ProfileController {

    private static final String PROFILE_VIEW_NAME = "profile";

    private UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/me")
    private String getMyProfile(Model model, Principal principal) {
        log.info("---> Fetching data from database for user = " + principal.getName() + " ...");
        User currentUser = userService.findByUsername(principal.getName());
        model.addAttribute("user", currentUser);
        return PROFILE_VIEW_NAME;
    }

    @RequestMapping("/{username}")
    private String getUserProfile(@PathVariable("username") String username, Model model) {
        log.info("---> Fetching data from database for user = " + username + " ...");
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return PROFILE_VIEW_NAME;
    }

}
