package hr.tvz.java.teambuildingbooking.validator;

import hr.tvz.java.teambuildingbooking.model.form.NewOfferForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Component
public class NewOfferFormValidator implements Validator {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String AVAILABLE_TO = "availableTo";
    private static final String DESCRIPTION = "description";

    @Override
    public boolean supports(Class<?> clazz) {
        return NewOfferForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NewOfferForm newOfferForm = (NewOfferForm) target;

        validateNameString(newOfferForm.getName(), errors);
        validateCategories(newOfferForm.getCategories(), errors);
        validateCountryString(newOfferForm.getCountry(), errors);
        validateCityString(newOfferForm.getCity(), errors);
        validateDescriptionString(newOfferForm.getDescription(), errors);
        validateAvailableFrom(newOfferForm.getAvailableFrom(), errors);
        validateAvailableTo(newOfferForm.getAvailableTo(), errors);
        validateDates(newOfferForm.getAvailableFrom(), newOfferForm.getAvailableTo(), errors);
        validateMinNumberOfPersons(newOfferForm.getMinNumberOfUsers(), errors);
        validateMaxNumberOfPersons(newOfferForm.getMaxNumberOfUsers(), errors);
        validatePricePerPerson(newOfferForm.getPricePerPerson(), errors);
    }

    private void validateCategories(List<String> categories, Errors errors) {
        if (categories == null || categories.isEmpty()) {
            errors.rejectValue("categories", "error.offer.categories.empty", "Morate unijeti barem jednu kategoriju.");
        }
    }

    private void validatePricePerPerson(int pricePerPerson, Errors errors) {
        if (isBlank(String.valueOf(pricePerPerson))) {
            errors.rejectValue("pricePerPerson", "error.offer.pricePerPerson.empty", "Morate unijeti cijenu po osobi.");
        }
    }

    private void validateMinNumberOfPersons(int minNumberOfUsers, Errors errors) {
        if (isBlank(String.valueOf(minNumberOfUsers))) {
            errors.rejectValue("minNumberOfUsers", "error.offer.minNumberOfUsers.empty", "Morate unijeti najmanji broj osoba.");
        }

        if (minNumberOfUsers < 1) {
            errors.rejectValue("minNumberOfUsers", "error.offer.minNumberOfUsers", "Najmanji broj korisnika mora biti veći od 1!");
        }
    }

    private void validateMaxNumberOfPersons(int maxNumberOfUsers, Errors errors) {
        if (isBlank(String.valueOf(maxNumberOfUsers))) {
            errors.rejectValue("maxNumberOfUsers", "error.offer.maxNumberOfUsers.empty", "Morate unijeti najveći broj osoba.");
        }

        if (maxNumberOfUsers > 300) {
            errors.rejectValue("maxNumberOfUsers", "error.offer.maxNumberOfUsers", "najveći broj korisnika mora biti manji od 300!");
        }
    }


    @SuppressWarnings("Duplicates")
    private void validateDates(String availableFrom, String availableTo, Errors errors) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

        Date availableFromDate = new Date();
        Date availableToDate = new Date();

        try {
            availableFromDate = simpleDateFormat.parse(availableFrom);
            availableToDate = simpleDateFormat.parse(availableTo);
        } catch (ParseException e) {
            errors.rejectValue(AVAILABLE_TO, "error.offer.dates.wrong-format", "Krivi format unešenog datuma!");
        }

        if (availableFromDate.after(availableToDate)) {
            errors.rejectValue(AVAILABLE_TO, "error.offer.dates", "Datum početka ponude ne može biti nakon datuma završetka ponude!");
        }

        if (availableToDate.before(availableFromDate)) {
            errors.rejectValue(AVAILABLE_TO, "error.offer.dates", "Datum završetka ponude ne može biti prije datuma početka ponude!");
        }

    }

    void validateNameString(String name, Errors errors) {
        if (isBlank(name)) {
            errors.rejectValue("name", "error.offer.name.empty", "Morate unijeti naziv.");
        }

        if (name.length() > 30) {
            errors.rejectValue("name", "error.offer.name.too-long", "Naziv ne može biti duže od 30 znakova.");
        }
    }

    void validateCountryString(String country, Errors errors) {
        if (isBlank(country)) {
            errors.rejectValue("country", "error.offer.country.empty", "Morate unijeti državu.");
        }

        if (country.length() > 30) {
            errors.rejectValue("country", "error.offer.country.too-long", "Država ne može biti duže od 30 znakova.");
        }
    }

    void validateCityString(String city, Errors errors) {

        if (isBlank(city)) {
            errors.rejectValue("city", "error.offer.city.empty", "Morate unijeti grad!");
        }

        if (city.length() > 30) {
            errors.rejectValue("city", "error.offer.city.too-long", "Grad ne može biti duži od 30 znakova!");
        }

    }

    void validateDescriptionString(String description, Errors errors) {

        if (isBlank(description)) {
            errors.rejectValue(DESCRIPTION, "error.offer.description.empty", "Morate unijeti opis ponude.");
        }

        if (description.length() > 200) {
            errors.rejectValue(DESCRIPTION, "error.offer.description.too-long", "Opis ponude ne može biti dulji od 200 znakova.");
        }

        if (description.length() < 5) {
            errors.rejectValue(DESCRIPTION, "error.offer.description.too-short", "Opis ponude ime mora imati barem 5 znakova.");
        }

    }

    private void validateAvailableFrom(String availableFrom, Errors errors) {
        if (isBlank(availableFrom)) {
            errors.rejectValue("availableFrom", "error.offer.availableFrom.empty", "Morate unijeti datum od kada ponuda postaje dostupna.");
        }
    }

    private void validateAvailableTo(String availableTo, Errors errors) {
        if (isBlank(availableTo)) {
            errors.rejectValue(AVAILABLE_TO, "error.offer.availableTo.empty", "Morate unijeti datum do kada je ponuda dostupna.");
        }
    }

}