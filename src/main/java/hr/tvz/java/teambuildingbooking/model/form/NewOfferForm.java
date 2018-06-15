package hr.tvz.java.teambuildingbooking.model.form;

import java.util.List;

public class NewOfferForm {

    private List<String> categories;

    private String name;

    private String country;

    private String city;

    private String address;

    private int minNumberOfUsers;

    private int maxNumberOfUsers;

    private int pricePerPerson;

    private String availableFrom;

    private String availableTo;

    private String description;

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMinNumberOfUsers() {
        return minNumberOfUsers;
    }

    public void setMinNumberOfUsers(int minNumberOfUsers) {
        this.minNumberOfUsers = minNumberOfUsers;
    }

    public int getMaxNumberOfUsers() {
        return maxNumberOfUsers;
    }

    public void setMaxNumberOfUsers(int maxNumberOfUsers) {
        this.maxNumberOfUsers = maxNumberOfUsers;
    }

    public int getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(int pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    public String getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(String availableFrom) {
        this.availableFrom = availableFrom;
    }

    public String getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(String availableTo) {
        this.availableTo = availableTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
