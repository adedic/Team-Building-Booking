package hr.tvz.java.teambuildingbooking.validator;

import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.EditUserForm;
import hr.tvz.java.teambuildingbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Component
public class EditUserFormValidator implements Validator {


    private static final String SURNAME = "surname";
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";

    private final UserService userService;

    @Autowired
    public EditUserFormValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return EditUserForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EditUserForm editUserForm = (EditUserForm) target;

        validateUsername(editUserForm.getUsername(), errors, editUserForm.getId());
        validateEmail(editUserForm.getEmail(), errors, editUserForm.getId());
        validateDateOfBirth(editUserForm.getDateOfBirth(), errors);

        validateUsernameString(editUserForm.getUsername(), errors);
        validateFirstNameString(editUserForm.getName(), errors);
        validateLastNameString(editUserForm.getSurname(), errors);
        validateEmailString(editUserForm.getEmail(), errors);

    }

    private void validateDateOfBirth(String dateOfBirth, Errors errors) {
        if (isBlank(dateOfBirth)) {
            errors.rejectValue("dateOfBirth", "error.user.dateOfBirth.empty", "Morate unijeti datum rođenja.");
        }
    }

    void validateLastNameString(String name, Errors errors) {
        if (isBlank(name)) {
            errors.rejectValue(SURNAME, "error.user.username.empty", "Morate unijeti ime.");
        }

        if (name.length() > 30) {
            errors.rejectValue(SURNAME, "error.user.surname.too-long", "Prezime ne može biti duže od 30 znakova.");
        }
    }

    void validateFirstNameString(String surname, Errors errors) {
        if (isBlank(surname)) {
            errors.rejectValue(SURNAME, "error.user.surname.empty", "Morate unijeti ime.");
        }

        if (surname.length() > 30) {
            errors.rejectValue(SURNAME, "error.user.surname.too-long", "Ime ne može biti duže od 30 znakova.");
        }
    }

    void validateEmailString(String email, Errors errors) {

        if (isBlank(email)) {
            errors.rejectValue(EMAIL, "error.user.email.empty", "Morate unijeti email!");
        }

        if (email.length() > 30) {
            errors.rejectValue(EMAIL, "error.user.email.too-long", "Email ne može biti duži od 30 znakova!");
        }

    }

    void validateUsernameString(String username, Errors errors) {

        if (isBlank(username)) {
            errors.rejectValue(USERNAME, "error.user.username.empty", "Morate unijeti korisničko ime.");
        }

        if (username.length() > 15) {
            errors.rejectValue(USERNAME, "error.user.username.too-long", "Korisničko ime ne može biti duže od 15 znakova.");
        }

        if (username.length() < 5) {
            errors.rejectValue(USERNAME, "error.user.username.too-short", "Korisničko ime mora imati barem 5 znakova");
        }

    }

    private void validateUsername(String username, Errors errors, Long id) {
        User user = userService.getById(id);
        String oldUsername = user.getUsername();

        if (userService.existsByUsernameIgnoreCase(username) && !username.equals(oldUsername)) {
            errors.rejectValue(USERNAME, "error.user.already-exists", "Već postoji korisnik s tim korisničkim imenom u bazi podataka.");
        }
    }

    private void validateEmail(String email, Errors errors, Long id) {
        User user = userService.getById(id);
        String oldEmail = user.getEmail();

        if (userService.existsByEmailIgnoreCase(email) && !email.equals(oldEmail)) {
            errors.rejectValue(EMAIL, "error.email.already-exists", "Već postoji korisnik s tim emailom u bazi podataka.");
        }
    }

}