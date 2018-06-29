package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.OfferPicture;
import hr.tvz.java.teambuildingbooking.service.OfferPictureService;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferPictureServiceImplTest {

    private static final Long EXISTING_OFFER_PICTURE_ID = 1L;
    private static final Long EXISTING_OFFER_ID = 1L;
    private static final Long NON_EXISTING_OFFER_PICTURE_ID = 99999999L;

    @Autowired
    private OfferPictureService offerPictureService;

    @Autowired
    private OfferService offerService;

    @Test
    public void save() {
        // arrange ...
        OfferPicture offerPicture = new OfferPicture();
        offerPicture.setName("slika");
        offerPicture.setBase64String("");

        Offer offer = offerService.findOne(EXISTING_OFFER_ID).get();

        offerPicture.setOffer(offer);
        offerPicture.setSize(400);

        // act ...
        OfferPicture offerPictureSaved = offerPictureService.save(offerPicture);

        // assert ...
        assertNotNull(offerPictureSaved);
        assertEquals(offerPictureSaved.getOffer().getId(), offer.getId());
    }

    @Test
    public void findById_WhenExists() {
        // arrange ...

        // act ...
        OfferPicture offerPicture = offerPictureService.findById(EXISTING_OFFER_PICTURE_ID);

        // assert ...
        assertNotNull(offerPicture);
        assertEquals(EXISTING_OFFER_PICTURE_ID, offerPicture.getId());
    }

    @Test(expected = LazyInitializationException.class)
    public void findById_WhenDoesNotExist() {
        // arrange ...

        // act ...
        OfferPicture offerPicture = offerPictureService.findById(NON_EXISTING_OFFER_PICTURE_ID);

        // assert ...
        assertNull(offerPicture);
    }
}