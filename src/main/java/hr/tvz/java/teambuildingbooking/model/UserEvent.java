package hr.tvz.java.teambuildingbooking.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "USER_EVENT")
public class UserEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_EVENT_ID", nullable = false)
    private Long id;

    //@Column(name = "DATA_ONE")
    //private String dataOne;

    //@Column(name = "DATA_TWO")
    //private String dataTwo;

    @Column(name = "DATE_OF_EVENT", nullable = false)
    private Date dateOfEvent;

    //@Column(name = "TIMESTAMP", nullable = false)
    //private Timestamp tStamp;
}