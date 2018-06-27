package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.OfferPicture;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.EditOfferForm;
import hr.tvz.java.teambuildingbooking.model.form.NewOfferForm;
import hr.tvz.java.teambuildingbooking.service.CategoryService;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import hr.tvz.java.teambuildingbooking.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferServiceImplTest {

    @Autowired
    OfferService offerService;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Test
    public void findAll() {
        assertNotNull(offerService.findAll());
    }

    @Test
    public void findOne() {
        assertNotNull(offerService.findOne(new Long(1)));
    }

    @Test
    public void findOffers() {
    }

    @Test
    public void isOfferValid() {
    }

    @Test
    public void save() {
        OfferPicture op = new OfferPicture();
        op.setBase64String("");
        op.setSize(400);
        op.setName("slika");

        Offer offer = new Offer();
        offer.setName("ime");
        offer.setDescription("opis");
        offer.setAddress("adr");
        offer.setActive(true);
        offer.setAvailableFrom(new Date());
        offer.setAvailableTo(new Date());
        offer.setCity("Grad");
        offer.setCountry("Drzava");
        offer.setDateAdded(new Date());
        offer.setMaxNumberOfUsers(10);
        offer.setMinNumberOfUsers(3);
        offer.setPricePerPerson(100.0);

        offer.setOfferPicture(op);

        assertNotNull(offerService.save(offer));
    }

    @Test
    public void findTopOffers() {
        assertNotNull(offerService.findTopOffers());
    }

    @Test
    public void findOffersByUserOrderByDateAdded() {
        User user = userService.findByUsername("user");
        assertNotNull(offerService.findOffersByUserOrderByDateAdded(user));
    }

    @Test
    public void createOffer() throws ParseException {
        NewOfferForm newOfferForm = new NewOfferForm();

        newOfferForm.setName("ime");
        newOfferForm.setAddress("adr");
        newOfferForm.setAvailableFrom("2018-06-15");
        newOfferForm.setAvailableTo("2018-06-25");
        newOfferForm.setCity("Grad");
        newOfferForm.setCountry("Drzava");
        newOfferForm.setMaxNumberOfUsers(10);
        newOfferForm.setMinNumberOfUsers(3);
        newOfferForm.setPricePerPerson(100);
        newOfferForm.setDescription("bla");

        /*List<String> categories = new ArrayList<>();
        categories.add("Sport");

        newOfferForm.setCategories(categories);*/
        newOfferForm.setCategories(new ArrayList<>());

        String base64String = "";
        String name = "ponuda slika";
        Integer size= 400;
        String username = "user";

        assertNotNull(offerService.createOffer(newOfferForm, base64String, name, size, username));
    }

    @Test
    public void editOffer() throws ParseException{
        EditOfferForm editOfferForm = new EditOfferForm();

        editOfferForm.setId(new Long(1));
        editOfferForm.setName("ime");
        editOfferForm.setAddress("adr");
        editOfferForm.setAvailableFrom("2018-06-15");
        editOfferForm.setAvailableTo("2018-06-25");
        editOfferForm.setCity("Grad");
        editOfferForm.setCountry("Drzava");
        editOfferForm.setMaxNumberOfUsers(10);
        editOfferForm.setMinNumberOfUsers(3);
        editOfferForm.setPricePerPerson(100);
        editOfferForm.setDateAdded("2018-06-10");
        editOfferForm.setDescription("blablaaaa");

        editOfferForm.setCategories(new ArrayList<>());

        String base64String = "";
        String name = "ponuda slika";
        Integer size= 400;
        String username = "user";

        assertNotNull(offerService.editOffer(editOfferForm, base64String, name, size, username));
    }

    @Test
    public void getOfferPictureIdByOfferId() {
        assertNotNull(offerService.getOfferPictureIdByOfferId(new Long(1)));
    }

}