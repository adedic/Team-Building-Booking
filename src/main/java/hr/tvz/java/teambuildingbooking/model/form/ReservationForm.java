package hr.tvz.java.teambuildingbooking.model.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class ReservationForm {

    private Long offerId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    private String dateString;

    private Integer numberOfUsers;


    public ReservationForm () {}

    public ReservationForm (Long offerId, Date date, Integer numberOfUsers) {
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
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return formatter.parse(this.dateString);
            } catch (ParseException e) {
               log.error(e.getMessage());
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

    public Integer getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(Integer numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }
}
