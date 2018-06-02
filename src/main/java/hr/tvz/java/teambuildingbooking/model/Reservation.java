package hr.tvz.java.teambuildingbooking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "RESERVATION")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OFFER_ID", referencedColumnName = "ID")
    private Offer offer;

    @Column(name = "STARTS_AT", nullable = false)
    private Date startsAt;

    @Column(name = "ENDS_AT", nullable = false)
    private Date endsAt;

    @Column(name = "DATE_OF_RESERVATION", nullable = false)
    private Date dateOfReservation;

    @Column(name = "DATE_OF_CANCELLATION")
    private Date dateOfCancellation;

    @Column(name = "DATE_LAST_EDITED")
    private Date dateLastEdited;

    public Reservation() {
        // default constructor
    }

    // --- get / set methods --------------------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Offer getOffer() {
        return offer;
    }

    @JsonIgnore
    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Date getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(Date startsAt) {
        this.startsAt = startsAt;
    }

    public Date getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(Date endsAt) {
        this.endsAt = endsAt;
    }

    public Date getDateOfReservation() {
        return dateOfReservation;
    }

    public void setDateOfReservation(Date dateOfReservation) {
        this.dateOfReservation = dateOfReservation;
    }

    public Date getDateOfCancellation() {
        return dateOfCancellation;
    }

    public void setDateOfCancellation(Date dateOfCancellation) {
        this.dateOfCancellation = dateOfCancellation;
    }

    public Date getDateLastEdited() {
        return dateLastEdited;
    }

    public void setDateLastEdited(Date dateLastEdited) {
        this.dateLastEdited = dateLastEdited;
    }
}
