package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.OfferPicture;
import hr.tvz.java.teambuildingbooking.service.OfferPictureService;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferPictureServiceImplTest {

    @Autowired
    OfferPictureService offerPictureService;
    @Autowired
    OfferService offerService;

    @Test
    public void save() {
        OfferPicture offerPicture = new OfferPicture();
        offerPicture.setName("slika");
        offerPicture.setBase64String("");

        offerPicture.setOffer( offerService.findOne(new Long(1)).orElse(new Offer()));
        offerPicture.setSize(400);

        assertNotNull(offerPictureService.save(offerPicture));
    }

    @Test
    public void findById() {
        Long offerId = new Long(1);
        assertNotNull(offerPictureService.findById(offerId));
    }
}