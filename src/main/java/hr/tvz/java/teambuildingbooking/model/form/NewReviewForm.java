package hr.tvz.java.teambuildingbooking.model.form;

public class NewReviewForm {
    private int numberOfStars;
    private String comment;

    public int getNumberOfStars() {
        return numberOfStars;
    }

    public void setNumberOfStars(int numberOfStars) {
        this.numberOfStars = numberOfStars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
