package hr.tvz.java.teambuildingbooking.facade.impl;

import hr.tvz.java.teambuildingbooking.facade.OfferFacade;
import hr.tvz.java.teambuildingbooking.mapper.OfferMapper;
import hr.tvz.java.teambuildingbooking.model.Category;
import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.form.EditOfferForm;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class OfferFacadeImpl implements OfferFacade {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public EditOfferForm mapOfferToEditOfferForm(Offer offer) {

        EditOfferForm editOfferForm = OfferMapper.INSTANCE.offerToEditOfferForm(offer);

        if (offer.getAvailableFrom() != null) {
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            editOfferForm.setAvailableFrom(dateFormat.format(offer.getAvailableFrom()));
        }

        if (offer.getAvailableTo() != null) {
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            editOfferForm.setAvailableTo(dateFormat.format(offer.getAvailableTo()));
        }

        if (offer.getDateAdded() != null) {
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            editOfferForm.setDateAdded(dateFormat.format(offer.getDateAdded()));
        }

        editOfferForm.setName(offer.getName());
        editOfferForm.setAddress(offer.getAddress());

        List<String> mappedCategories = new ArrayList<>();

        for (Category category : offer.getCategories()) {
            mappedCategories.add(category.getName());
        }

        editOfferForm.setCategories(mappedCategories);


        return editOfferForm;
    }
}
