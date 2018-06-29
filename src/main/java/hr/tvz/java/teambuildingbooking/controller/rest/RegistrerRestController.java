package hr.tvz.java.teambuildingbooking.controller.rest;

import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.RegistrationForm;
import hr.tvz.java.teambuildingbooking.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Slf4j
@RestController
@RequestMapping(path="/rest", produces="application/json")
@CrossOrigin(origins = "*")
public class RegistrerRestController {

    private UserService userService;

    @Autowired
    public RegistrerRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegistrationForm form){

        User user = null;

        try {
            userService.createUser(form);
            user = userService.findByUsername(form.getUsername());
        } catch (ParseException e) {
            log.info(e.getMessage());
        }

        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);

    }
}
