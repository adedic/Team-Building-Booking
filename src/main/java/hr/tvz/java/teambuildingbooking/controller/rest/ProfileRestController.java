package hr.tvz.java.teambuildingbooking.controller.rest;

import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.EditUserForm;
import hr.tvz.java.teambuildingbooking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(path="/rest/profile", produces="application/json")
@CrossOrigin(origins = "*")
public class ProfileRestController {

    private UserService userService;

    public ProfileRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me/{username}")
    private ResponseEntity<User> getMyProfile(@PathVariable String username) {

        User user = userService.findByUsername(username);

        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);

    }

    @GetMapping("/{username}")
    private ResponseEntity<User> getUserProfile(@PathVariable String username) {

        User user = userService.findByUsername(username);

        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);

    }

    @GetMapping("/edit/{username}")
    private ResponseEntity<User> editProfile(@PathVariable String username) {

        User user = userService.findByUsername(username);

        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);

    }

    @PutMapping("/edit")
    private ResponseEntity<User> editProfile(@RequestBody EditUserForm editUserForm) {

        User user = null;

        try {
            userService.editUser(editUserForm, editUserForm.getUsername());
        } catch (ParseException e) {
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }

        user = userService.findByUsername(editUserForm.getUsername());

        return new ResponseEntity<>(user, HttpStatus.OK);

    }
}
