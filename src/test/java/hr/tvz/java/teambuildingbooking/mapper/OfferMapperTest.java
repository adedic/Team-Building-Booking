package hr.tvz.java.teambuildingbooking.mapper;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.OfferPicture;
import hr.tvz.java.teambuildingbooking.model.form.EditOfferForm;
import hr.tvz.java.teambuildingbooking.model.form.NewOfferForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferMapperTest {

    @Test
    public void newOfferFormToOffer() {
        // arrange ...
        NewOfferForm newOfferForm = new NewOfferForm();
        newOfferForm.setName("ime");
        newOfferForm.setAddress("adr");
        newOfferForm.setCity("Grad");
        newOfferForm.setCountry("Drzava");
        newOfferForm.setMaxNumberOfUsers(10);
        newOfferForm.setMinNumberOfUsers(3);
        newOfferForm.setPricePerPerson(100);
        newOfferForm.setDescription("bla");

        // act ...
        Offer offer = OfferMapper.INSTANCE.newOfferFormToOffer(newOfferForm);

        // assert ...
        assertNotNull(offer);
        assertEquals(newOfferForm.getName(), offer.getName());
        assertEquals(newOfferForm.getAddress(), offer.getAddress());

        assertEquals(newOfferForm.getCity(), offer.getCity());
        assertEquals(newOfferForm.getCountry(), offer.getCountry());
        assertEquals(Integer.valueOf(newOfferForm.getMaxNumberOfUsers()), offer.getMaxNumberOfUsers());
        assertEquals(Integer.valueOf(newOfferForm.getMinNumberOfUsers()), offer.getMinNumberOfUsers());
        assertEquals(Double.valueOf(newOfferForm.getPricePerPerson()), offer.getPricePerPerson());
        assertEquals(newOfferForm.getDescription(), offer.getDescription());

    }

    @Test
    public void editOfferFormToOffer() {
        // arrange ...
        EditOfferForm editOfferForm = new EditOfferForm();

        editOfferForm.setId(new Long(1));
        editOfferForm.setName("ime");
        editOfferForm.setAddress("adr");
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

        // act ...
        Offer offer = OfferMapper.INSTANCE.editOfferFormToOffer(editOfferForm);

        // assert ...
        assertNotNull(offer);
        assertEquals(editOfferForm.getName(), offer.getName());
        assertEquals(editOfferForm.getAddress(), offer.getAddress());
        assertEquals(editOfferForm.getCity(), offer.getCity());
        assertEquals(editOfferForm.getCountry(), offer.getCountry());
        assertEquals(Integer.valueOf(editOfferForm.getMaxNumberOfUsers()), offer.getMaxNumberOfUsers());
        assertEquals(Integer.valueOf(editOfferForm.getMinNumberOfUsers()), offer.getMinNumberOfUsers());
        assertEquals(Double.valueOf(editOfferForm.getPricePerPerson()), offer.getPricePerPerson());
        assertEquals(editOfferForm.getDescription(), offer.getDescription());
    }

    @Test
    public void offerToEditOfferForm() {
        // arrange ...
        Offer offer = new Offer();
        offer.setName("ime");
        offer.setDescription("opis");
        offer.setAddress("adr");
        offer.setCity("Grad");
        offer.setCountry("Drzava");
        offer.setMaxNumberOfUsers(10);
        offer.setMinNumberOfUsers(3);
        offer.setPricePerPerson(100.0);

        // act ...
        EditOfferForm editOfferForm = OfferMapper.INSTANCE.offerToEditOfferForm(offer);

        // assert ...
        assertNotNull(editOfferForm);
        assertEquals(editOfferForm.getName(), offer.getName());
        assertEquals(editOfferForm.getDescription(), offer.getDescription());
        assertEquals(editOfferForm.getAddress(), offer.getAddress());
        assertEquals(editOfferForm.getAddress(), offer.getAddress());
        assertEquals(editOfferForm.getCity(), offer.getCity());
        assertEquals(editOfferForm.getCountry(), offer.getCountry());
        assertEquals(Integer.valueOf(editOfferForm.getMaxNumberOfUsers()), offer.getMaxNumberOfUsers());
        assertEquals(Integer.valueOf(editOfferForm.getMinNumberOfUsers()), offer.getMinNumberOfUsers());
        assertEquals(Double.valueOf(editOfferForm.getPricePerPerson()), offer.getPricePerPerson());
        assertEquals(editOfferForm.getDescription(), offer.getDescription());
    }
}