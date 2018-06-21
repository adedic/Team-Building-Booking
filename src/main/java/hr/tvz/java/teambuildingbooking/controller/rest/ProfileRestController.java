package hr.tvz.java.teambuildingbooking.controller.rest;

import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.EditUserForm;
import hr.tvz.java.teambuildingbooking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(path="/rest/profile", produces="application/json")
@CrossOrigin(origins = "*")
public class ProfileRestController {

    private UserService userService;

    public ProfileRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    private ResponseEntity<List<User>> findAll() {

        List<User> users = userService.findAll();

        if (users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/me/{username}")
    private ResponseEntity<User> getMyProfile(@PathVariable String username) {

        return getProfile(username);

    }

    @GetMapping("/{username}")
    private ResponseEntity<User> getUserProfile(@PathVariable String username) {

        return getProfile(username);

    }

    private ResponseEntity<User> getProfile(@PathVariable String username) {
        User user = userService.findByUsername(username);

        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/edit/{username}")
    private ResponseEntity<User> editProfile(@PathVariable String username) {

        return getProfile(username);

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
