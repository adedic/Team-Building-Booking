package hr.tvz.java.teambuildingbooking.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "OFFER_PICTURE")
public class OfferPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OFFER_PICTURE_ID", nullable = false)
    private Long id;

    @Column(name = "PICTURE_PATH", nullable = false)
    private String picturePath;

}
