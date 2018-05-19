package hr.tvz.java.teambuildingbooking.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "USER_EVENT")
public class UserEvent implements Serializable {

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

    public UserEvent() {
        // default constructor
    }

    // --- get / set methods --------------------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOfEvent() {
        return dateOfEvent;
    }

    public void setDateOfEvent(Date dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }
}
