package hr.tvz.java.teambuildingbooking.model.rest;

import hr.tvz.java.teambuildingbooking.model.form.EditOfferForm;

public class RestEditOffer {

    private EditOfferForm editOfferForm;
    private String base64string;
    private String fileName;
    private Integer fileSize;
    private String username;

    public EditOfferForm getEditOfferForm() {
        return editOfferForm;
    }

    public void setEditOfferForm(EditOfferForm editOfferForm) {
        this.editOfferForm = editOfferForm;
    }

    public String getBase64string() {
        return base64string;
    }

    public void setBase64string(String base64string) {
        this.base64string = base64string;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
