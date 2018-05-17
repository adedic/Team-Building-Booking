package hr.tvz.java.teambuildingbooking.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "RESERVATION")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`USER`", referencedColumnName = "ID")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OFFER", referencedColumnName = "OFFER_ID")
    private Offer offer;

    @Column(name = "DATE_OF", nullable = false)
    private Date dateOf;

    @Column(name = "DATE_TO", nullable = false)
    private Date dateTo;

    @Column(name = "DATE_OF_RESERVATION", nullable = false)
    private Date dateOfReservation;

    @Column(name = "DATE_OF_CANCELLATION", nullable = false)
    private Date dateOfCancellation;

    //@Column(name = "TIMESTAMP", nullable = false)
    //private Timestamp tStamp;


}
