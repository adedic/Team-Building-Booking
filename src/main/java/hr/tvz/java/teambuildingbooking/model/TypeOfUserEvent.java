package hr.tvz.java.teambuildingbooking.model;

import javax.persistence.*;

@Entity
@Table(name = "TYPE_OF_USER_EVENT")
public class
TypeOfUserEvent {

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

    public TypeOfUserEvent() {
        // default constructor
    }

    // --- get / set methods --------------------------------------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
