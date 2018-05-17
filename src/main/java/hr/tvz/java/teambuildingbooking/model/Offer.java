package hr.tvz.java.teambuildingbooking.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
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

    @Column(name = "COUNTRY", nullable = false)
    private String country;

    @Column(name = "CITY", nullable = false)
    private String city;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "OFFER_CATEGORY",
            joinColumns = {@JoinColumn(name = "OFFER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")}
    )
    @Fetch(FetchMode.JOIN)
    private Set<Category> roles = new HashSet<>();

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
    private boolean enabled;

    @Column(name = "ACTIVE", nullable = false)
    private boolean active;

    @Column(name = "DATE_ADDED", nullable = false)
    private Date dateAdded;

    @Column(name = "DATE_DELETED")
    private Date dateDeleted;

    @Column(name = "DATE_LAST_EDITED")
    private Date dateLastEdited;

}
