package hr.tvz.java.teambuildingbooking.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "OFFER_PICTURE")
public class OfferPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "BASE64_STRING", nullable = false)
    private String base64String;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "SIZE", nullable = false)
    private String size;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OFFER_ID", referencedColumnName = "ID")
    private Offer offer;

}
