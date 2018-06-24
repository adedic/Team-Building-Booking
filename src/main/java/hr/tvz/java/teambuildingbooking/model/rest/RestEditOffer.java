package hr.tvz.java.teambuildingbooking.model.rest;

import hr.tvz.java.teambuildingbooking.model.form.EditOfferForm;

public class RestEditOffer extends RestOffer {

    private EditOfferForm editOfferForm;

    public EditOfferForm getEditOfferForm() {
        return editOfferForm;
    }

    public void setEditOfferForm(EditOfferForm editOfferForm) {
        this.editOfferForm = editOfferForm;
    }

}
