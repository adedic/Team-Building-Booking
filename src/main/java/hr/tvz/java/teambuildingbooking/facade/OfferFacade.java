package hr.tvz.java.teambuildingbooking.facade;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.form.EditOfferForm;

public interface OfferFacade {

    EditOfferForm mapOfferToEditOfferForm(Offer offer);
}
