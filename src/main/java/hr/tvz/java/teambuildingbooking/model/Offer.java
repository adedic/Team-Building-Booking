package hr.tvz.java.teambuildingbooking.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "OFFER")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @Column(name = "STATE", nullable = false)
    private String state;

    @Column(name = "CITY", nullable = false)
    private String city;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "offer", cascade = CascadeType.REMOVE)
    private Set<Category> categories;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "offer", cascade = CascadeType.REMOVE)
    private Set<Feedback> feedbacks;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "offer", cascade = CascadeType.REMOVE)
    private Set<Reservation> reservations;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OFFER_PICTURE_ID", referencedColumnName = "ID")
    private OfferPicture offerPicture;

    @Column(name = "MIN_NUMBER_OF_USERS", nullable = false)
    private Integer minNumberOfUsers;

    @Column(name = "MAX_NUMBER_OF_USERS", nullable = false)
    private Integer maxNumberOfUsers;

    @Column(name = "PRICE_PER_PERSON", nullable = false)
    private Double pricePerPerson;

    @Column(name = "AVAILABLE_FROM", nullable = false)
    private Date availableFrom;

    @Column(name = "AVAILABLE_TO", nullable = false)
    private Date availableTo;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ENABLED", nullable = false)
    private Boolean enabled;

    //@Column(name = "DATE_OF_ADDING", nullable = false)
    //private Date dateOfAdding;

    //@Column(name = "DATE_OF_DELETING", nullable = false)
    //private Date dateOfDeleting;

    //@Column(name = "TIMESTAMP", nullable = false)
    //private Timestamp tStamp;

}
