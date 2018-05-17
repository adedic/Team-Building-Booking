package hr.tvz.java.teambuildingbooking.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

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

    @Column(name = "GRADE")
    private Integer grade;

    @Column(name = "DATE")
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OFFER_ID", referencedColumnName = "ID")
    private Offer offer;

    //@Column(name = "TIMESTAMP", nullable = false)
    //private Timestamp tStamp;

}
