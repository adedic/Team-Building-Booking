package hr.tvz.java.teambuildingbooking.controller;

import hr.tvz.java.teambuildingbooking.model.form.RegistrationForm;
import hr.tvz.java.teambuildingbooking.service.UserService;
import hr.tvz.java.teambuildingbooking.validator.RegistrationFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;

@Controller
public class RegisterController {

    private static final String REGISTER_VIEW_NAME = "register";

    private UserService userService;

    private RegistrationFormValidator registrationFormValidator;

    @Autowired
    public RegisterController(UserService userService, RegistrationFormValidator registrationFormValidator) {
        this.userService = userService;
        this.registrationFormValidator = registrationFormValidator;
    }

    @RequestMapping("/register")
    private String register(Model model) {
        model.addAttribute("navLinkText", "Prijava");

        model.addAttribute("registrationForm", new RegistrationForm());
        return REGISTER_VIEW_NAME;
    }

    @PostMapping(value = "/register")
    public String handleRegistrationForm(@Valid @ModelAttribute("registrationForm") RegistrationForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws ParseException {

        if (bindingResult.hasErrors()) {
            return REGISTER_VIEW_NAME;
        }
        try {
            userService.createUser(form);
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("email.exists", "Email already exists");
            return REGISTER_VIEW_NAME;
        }

        redirectAttributes.addFlashAttribute("registrationSuccess", "Registracija uspješna! Prijavite se sa podacima koje ste unijeli.");

        return "redirect:/login";
    }

    // form validators
    @InitBinder("registrationForm")
    public void addNewUserFormValidator(WebDataBinder dataBinder) {
        dataBinder.addValidators(registrationFormValidator);
    }

}