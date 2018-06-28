package hr.tvz.java.teambuildingbooking.mapper;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.OfferPicture;
import hr.tvz.java.teambuildingbooking.model.form.EditOfferForm;
import hr.tvz.java.teambuildingbooking.model.form.NewOfferForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferMapperTest {

    @Test
    public void newOfferFormToOffer() {
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

        List<String> categories = new ArrayList<>();
        categories.add("Sport");

        newOfferForm.setCategories(categories);
        newOfferForm.setCategories(new ArrayList<>());

        assertNotNull(OfferMapper.INSTANCE.newOfferFormToOffer(newOfferForm));
    }

    @Test
    public void editOfferFormToOffer() {
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
        List<String> categories = new ArrayList<>();
        categories.add("Sport");
        editOfferForm.setCategories(categories);

        assertNotNull(OfferMapper.INSTANCE.editOfferFormToOffer(editOfferForm));
    }

    @Test
    public void offerToEditOfferForm() {
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
        assertNotNull(OfferMapper.INSTANCE.offerToEditOfferForm(offer));
    }
}