package hr.tvz.java.teambuildingbooking.model.rest;

public class RestReservationResponse {

    private String message;
    private Error error;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

}
