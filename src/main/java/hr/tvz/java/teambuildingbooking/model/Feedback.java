package hr.tvz.java.teambuildingbooking.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "FEEDBACK")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "NUMBER_OF_STARS")
    private Integer numberOfStars;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OFFER_ID", referencedColumnName = "ID")
    private Offer offer;

    @Column(name = "DATE_SUBMITTED", nullable = false)
    private Date dateSubmitted;

    @Column(name = "DATE_LAST_EDITED")
    private Date dateLastEdited;

}
