package hr.tvz.java.teambuildingbooking.validator;

import hr.tvz.java.teambuildingbooking.model.form.NewReviewForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Component
public class NewReviewFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return NewReviewForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NewReviewForm newReviewForm = (NewReviewForm) target;

        validateComment(newReviewForm.getComment(), errors);
    }

    private void validateComment(String comment, Errors errors){
        if(isBlank(comment)){
            errors.rejectValue("comment", "error.review.comment.empty", "Morate unijeti osvrt.");
        }
    }

    private void validateNumberOfStars(int number, Errors errors){
        if(isBlank(String.valueOf(number))){
            errors.rejectValue("comment", "error.review.number.empty", "Morate unijeti ocjenu.");
        }
    }
}
