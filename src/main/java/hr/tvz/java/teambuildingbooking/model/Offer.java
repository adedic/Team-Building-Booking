package hr.tvz.java.teambuildingbooking.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "OFFER")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OFFER_ID", nullable = false)
    private Long id;

    @Column(name = "STATE", nullable = false)
    private String state;

    @Column(name = "CITY", nullable = false)
    private String city;

    @Column(name = "MIN_NUMBER_OF_USERS", nullable = false)
    private Integer minNumberOfUsers;

    @Column(name = "MAX_NUMBER_OF_USERS", nullable = false)
    private Integer maxNumberOfUsers;

    @Column(name = "PRICE_PER_PEARSON", nullable = false)
    private Double pricePerPearson;

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