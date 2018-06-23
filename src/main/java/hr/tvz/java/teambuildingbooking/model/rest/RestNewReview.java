package hr.tvz.java.teambuildingbooking.model.rest;

import hr.tvz.java.teambuildingbooking.model.form.NewReviewForm;

public class RestNewReview {

    private NewReviewForm newReviewForm;
    private String username;

    public NewReviewForm getNewReviewForm() {
        return newReviewForm;
    }

    public void setNewReviewForm(NewReviewForm newReviewForm) {
        this.newReviewForm = newReviewForm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
