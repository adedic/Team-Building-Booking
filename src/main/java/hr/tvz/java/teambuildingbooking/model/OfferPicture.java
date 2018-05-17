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

    @Column(name = "PICTURE_PATH", nullable = false)
    private String picturePath;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OFFER_ID", referencedColumnName = "ID")
    private Offer offer;


}
