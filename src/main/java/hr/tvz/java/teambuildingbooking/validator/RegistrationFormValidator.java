package hr.tvz.java.teambuildingbooking.validator;

import hr.tvz.java.teambuildingbooking.model.form.RegistrationForm;
import hr.tvz.java.teambuildingbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Component
public class RegistrationFormValidator implements Validator {

    private final UserService userService;

    @Autowired
    public RegistrationFormValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationForm registrationForm = (RegistrationForm) target;

        validateUsername(registrationForm.getUsername(), errors);
        validatePassword(registrationForm.getPassword(), registrationForm.getConfirmPassword(), errors);
        validateEmail(registrationForm.getEmail(), errors);
        validateRole(registrationForm.getUserRole(), errors);
        validateDateOfBirth(registrationForm.getDateOfBirth(), errors);

        validateUsernameString(registrationForm.getUsername(), errors);
        validatePasswordString(registrationForm.getPassword(), errors);
        validateMatchingPasswordString(registrationForm.getConfirmPassword(), errors);
        validateEmailString(registrationForm.getEmail(), errors);
        validatePhoneNumberString(registrationForm.getTelephone(), errors);
        validateFirstNameString(registrationForm.getName(), errors);
        validateLastNameString(registrationForm.getSurname(), errors);

    }

    private void validateRole(String dateOfBirth, Errors errors) {
        if (isBlank(dateOfBirth)) {
            errors.rejectValue("userRole", "error.user.userRole.empty", "Morate unijeti ulogu u sustavu.");
        }
    }

    private void validateDateOfBirth(String dateOfBirth, Errors errors) {
        if (isBlank(dateOfBirth)) {
            errors.rejectValue("dateOfBirth", "error.user.dateOfBirth.empty", "Morate unijeti datum rođenja.");
        }
    }

    void validateLastNameString(String name, Errors errors) {
        if (isBlank(name)) {
            errors.rejectValue("surname", "error.user.username.empty", "Morate unijeti ime.");
        }

        if (name.length() > 30) {
            errors.rejectValue("surname", "error.user.surname.too-long", "Prezime ne može biti duže od 30 znakova.");
        }
    }

    void validateFirstNameString(String surname, Errors errors) {
        if (isBlank(surname)) {
            errors.rejectValue("surname", "error.user.surname.empty", "Morate unijeti ime.");
        }

        if (surname.length() > 30) {
            errors.rejectValue("surname", "error.user.surname.too-long", "Ime ne može biti duže od 30 znakova.");
        }
    }

    void validatePhoneNumberString(String telephone, Errors errors) {

        if (isBlank(telephone)) {
            errors.rejectValue("telephone", "error.user.telephone.empty", "Morate unijeti telefon!");
        }

        if (telephone.length() > 20) {
            errors.rejectValue("telephone", "error.user.telephone.too-long", "Broj telefona ne može biti duži od 20 znakova!");
        }

    }

    void validateEmailString(String email, Errors errors) {

        if (isBlank(email)) {
            errors.rejectValue("email", "error.user.email.empty", "Morate unijeti email!");
        }

        if (email.length() > 30) {
            errors.rejectValue("email", "error.user.email.too-long", "Email ne može biti duži od 30 znakova!");
        }

    }

    void validateMatchingPasswordString(String confirmPassword, Errors errors) {

        if (isBlank(confirmPassword)) {
            errors.rejectValue("confirmPassword", "error.user.confirmPassword.empty", "Morate unijeti ponovljenu lozinku.");
        }

    }

    void validatePasswordString(String password, Errors errors) {

        if (isBlank(password)) {
            errors.rejectValue("password", "error.user.password.empty", "Morate unijeti lozinku.");
        }

    }

    void validateUsernameString(String username, Errors errors) {

        if (isBlank(username)) {
            errors.rejectValue("username", "error.user.username.empty", "Morate unijeti korisničko ime.");
        }

        if (username.length() > 15) {
            errors.rejectValue("username", "error.user.username.too-long", "Korisničko ime ne može biti duže od 15 znakova.");
        }

        if (username.length() < 5) {
            errors.rejectValue("username", "error.user.username.too-short", "Korisničko ime mora imati barem 5 znakova");
        }

    }

    private void validateUsername(String username, Errors errors) {
        if (userService.existsByUsernameIgnoreCase(username)) {
            errors.rejectValue("username", "error.user.already-exists", "Već postoji korisnik s tim korisničkim imenom u bazi podataka.");
        }

    }

    void validatePassword(String password, String confirmPassword, Errors errors) {
        if (password.length() < 8) {
            errors.rejectValue("password", "error.password.too-short", "Lozinka mora imati najmanje 8 znakova.");
        }

        if (confirmPassword.length() < 8) {
            errors.rejectValue("confirmPassword", "error.confirmPassword.too-short", "Potvrđena lozinka mora imati najmanje 8 znakova.");
        }

        if (!password.equals(confirmPassword)) {
            errors.rejectValue("password", "error.password.not-equal", "Unesene lozinke nisu jednake.");
        }

    }

    private void validateEmail(String email, Errors errors) {
        if (userService.existsByEmailIgnoreCase(email)) {
            errors.rejectValue("email", "error.email.already-exists", "Korisnik s tom email adresom već postoji u bazi podataka.");
        }
    }

}