package hr.tvz.java.teambuildingbooking.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ROLE")
public class Role implements GrantedAuthority, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, length = 30)
    private Integer id;

    @Column(name = "NAME", nullable = false, length = 40)
    private String name;

    public Role() {
        // default constructor
    }

    public Role(String name) {
        this.name = name;
    }

    // --- get / set methods --------------------------------------------------

    @Override
    public String getAuthority() { return getName(); }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }
}
