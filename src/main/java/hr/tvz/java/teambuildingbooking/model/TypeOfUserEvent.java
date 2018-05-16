package hr.tvz.java.teambuildingbooking.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TYPE_OF_USER_EVENT")
public class TypeOfUserEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TYPE_OF_EVENT_ID", nullable = false)
    private String id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    //@Column(name = "DATA_ONE")
    //private String dataOne;

    //@Column(name = "DATA_TWO")
    //private String dataTwo;

}
