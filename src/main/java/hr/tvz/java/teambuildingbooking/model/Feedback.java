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
    @Column(name = "FEEDBACK_ID", nullable = false)
    private Long id;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "GRADE")
    private Integer grade;

    @Column(name = "DATE")
    private Date date;

    //@Column(name = "TIMESTAMP", nullable = false)
    //private Timestamp tStamp;

}