package hr.tvz.java.teambuildingbooking.model.rest;

import hr.tvz.java.teambuildingbooking.model.form.NewOfferForm;

public class RestNewOffer extends RestOffer {

    private NewOfferForm editOfferForm;

    public NewOfferForm getEditOfferForm() {
        return editOfferForm;
    }

    public void setEditOfferForm(NewOfferForm editOfferForm) {
        this.editOfferForm = editOfferForm;
    }

}
