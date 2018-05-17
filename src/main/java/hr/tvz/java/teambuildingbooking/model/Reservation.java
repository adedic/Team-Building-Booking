package hr.tvz.java.teambuildingbooking.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "RESERVATION")
public class Reservation {

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

}
