package hr.tvz.java.teambuildingbooking.model.form;

import org.springframework.stereotype.Service;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservationForm {

    private Long offerId;

    private Date date;

    private String dateString;

    private Long numberOfUsers;


    public ReservationForm () {}

    public ReservationForm (Long offerId, Date date, Long numberOfUsers) {
        this.offerId = offerId;
        this.date = date;
        this.numberOfUsers = numberOfUsers;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Date getDate() {
        if (this.date != null) {
            return date;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            try {
                Date date = formatter.parse(this.dateString);
                return date;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public Long getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(Long numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }
}
