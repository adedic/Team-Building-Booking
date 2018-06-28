package hr.tvz.java.teambuildingbooking.controller;

import hr.tvz.java.teambuildingbooking.mapper.UserMapper;
import hr.tvz.java.teambuildingbooking.model.Reservation;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.EditUserForm;
import hr.tvz.java.teambuildingbooking.repository.ReservationRepository;
import hr.tvz.java.teambuildingbooking.service.ReservationService;
import hr.tvz.java.teambuildingbooking.service.UserService;
import hr.tvz.java.teambuildingbooking.validator.EditUserFormValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/profile")
public class ProfileController {

    private static final String PROFILE_VIEW_NAME = "/profile/profile";
    private static final String EDIT_PROFILE_VIEW_NAME = "/profile/edit-profile";
    private static final String MY_RESERVATIONS = "/reservation/myReservations";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private UserService userService;
    private ReservationService reservationService;
    private ReservationRepository reservationRepository;

    private EditUserFormValidator editUserFormValidator;

    @Autowired
    public ProfileController(UserService userService, EditUserFormValidator editUserFormValidator, ReservationService reservationService, ReservationRepository reservationRepository) {
        this.userService = userService;
        this.editUserFormValidator = editUserFormValidator;
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
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

    @RequestMapping("/edit")
    private String editProfile(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        EditUserForm editUserForm = UserMapper.INSTANCE.userToUserEditForm(user);

        if (user.getDateOfBirth() != null) {
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            editUserForm.setDateOfBirth(dateFormat.format(user.getDateOfBirth()));
        }

        model.addAttribute("editUserForm", editUserForm);
        return EDIT_PROFILE_VIEW_NAME;
    }

    @PostMapping("/edit")
    private String editProfile(@Valid @ModelAttribute("editUserForm") EditUserForm editUserForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, Principal principal) throws ParseException {

        if (bindingResult.hasErrors()) {
            return EDIT_PROFILE_VIEW_NAME;
        }

        userService.editUser(editUserForm, principal.getName());
        log.info("---> Updating data from database for user = " + principal.getName() + " ...");

        redirectAttributes.addFlashAttribute("editSuccess", "Uređivanje uspješno! Prijavite se ponovno kako bi vidjeli izmjene.");

        return "redirect:/logout?editSuccess";
    }

    @GetMapping("/myReservations")
    private String myReservations(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            List<Reservation> userReservations = reservationService.getAllReservationsByUser(user.getId());
            model.addAttribute("reservations", userReservations);
            return MY_RESERVATIONS;
        }
    }

    @PostMapping("/myReservations/cancelReservation")
    private String cancelReservation(@RequestParam Long reservationId) {
        try {
            Reservation reservation = new Reservation();
            Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
            if(reservationOptional.isPresent()){
                reservation = reservationOptional.get();
            }
            reservation.setCanceled(true);
            reservationRepository.saveAndFlush(reservation);
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return "redirect:/profile/myReservations";
    }

    // form validators
    @InitBinder("editUserForm")
    public void addNewUserFormValidator(WebDataBinder dataBinder) {
        dataBinder.addValidators(editUserFormValidator);
    }
}