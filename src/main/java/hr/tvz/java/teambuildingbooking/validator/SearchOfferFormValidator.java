package hr.tvz.java.teambuildingbooking.validator;

import hr.tvz.java.teambuildingbooking.model.form.SearchOfferForm;
import hr.tvz.java.teambuildingbooking.service.CategoryService;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SearchOfferFormValidator implements Validator {

    private final OfferService offerService;
    private final CategoryService categoryService;

    @Autowired
    public SearchOfferFormValidator(OfferService offerService, CategoryService categoryService) {
        this.offerService = offerService;
        this.categoryService = categoryService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SearchOfferForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SearchOfferForm searchOfferForm = (SearchOfferForm) target;

        validateCategory(searchOfferForm.getCategory(), errors);
        validateNumOfPeople(searchOfferForm.getNumOfPeople(), errors);
        validateCountry(searchOfferForm.getCountry(), errors);
        validateCity(searchOfferForm.getCity(), errors);
        validateDate(searchOfferForm.getDate(), errors);
    }

    private void validateCategory(String category, Errors errors) {
        if(category != null && !category.equals("")) {
            if(!categoryService.existsByNameIgnoreCase(category)) {
                errors.rejectValue("category", "error.searchOffer.category", "Izaberite jednu od postojećih kategorija");
            }
        }
    }

    private void validateNumOfPeople(Integer numOfPeople, Errors errors) {
        if(numOfPeople != null) {
            if(numOfPeople <= 0 || numOfPeople > 250) {
                errors.rejectValue("numOfPeople", "error.searchOffer.numOfPeople", "Broj ljudi mora biti veći od 0 i manji ili jednak 250.");
            }
        }
    }

    private void validateCountry(String country, Errors errors) {
        if(country != null && !country.equals("")) {
            Pattern pattern = Pattern.compile("\\w{2,100}");
            Matcher matcher = pattern.matcher(country);
            if (!matcher.matches()) {
                errors.rejectValue("country", "error.searchOffer.country", "Unesite valjani naziv države.");
            }
        }
    }

    private void validateCity(String city, Errors errors) {
        if(city != null && !city.equals("")) {
            Pattern pattern = Pattern.compile("\\w{2,100}");
            Matcher matcher = pattern.matcher(city);
            if (!matcher.matches()) {
                errors.rejectValue("city", "error.searchOffer.city", "Unesite valjani naziv grada.");
            }
        }
    }

    private void validateDate(String date, Errors errors) {
        if(date != null) {
        Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}\\.");
            Matcher matcher = pattern.matcher(date);
            if(!matcher.matches()) {
                errors.rejectValue("date", "error.searchOffer.date", "Unesite valjan datum.");
            }
        }
    }
}
