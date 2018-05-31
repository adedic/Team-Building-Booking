package hr.tvz.java.teambuildingbooking.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "OFFER")
@SequenceGenerator(name = "seq", initialValue = 6, allocationSize = 100)
public class Offer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @Column(name = "COUNTRY", nullable = false)
    private String country;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "CITY", nullable = false)
    private String city;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "OFFER_CATEGORY",
            joinColumns = {@JoinColumn(name = "OFFER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")}
    )
    @Fetch(FetchMode.JOIN)
    private Set<Category> categories = new HashSet<>();

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Feedback> feedbacks;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Reservation> reservations;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "OFFER_PICTURE_ID", referencedColumnName = "ID")
    private OfferPicture offerPicture;

    @Column(name = "MIN_NUMBER_OF_USERS", nullable = false)
    private Integer minNumberOfUsers;

    @Column(name = "MAX_NUMBER_OF_USERS", nullable = false)
    private Integer maxNumberOfUsers;

    @Column(name = "PRICE_PER_PERSON", nullable = false)
    private Double pricePerPerson;

    @Column(name = "AVAILABLE_FROM", nullable = false)
    private Date availableFrom;

    @Column(name = "AVAILABLE_TO", nullable = false)
    private Date availableTo;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled;

    @Column(name = "ACTIVE", nullable = false)
    private boolean active;

    @Column(name = "DATE_ADDED", nullable = false)
    private Date dateAdded;

    @Column(name = "DATE_DELETED")
    private Date dateDeleted;

    @Column(name = "DATE_LAST_EDITED")
    private Date dateLastEdited;

    public Offer() {
        // default constructor
    }

    // --- get / set methods --------------------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public OfferPicture getOfferPicture() {
        return offerPicture;
    }

    public void setOfferPicture(OfferPicture offerPicture) {
        this.offerPicture = offerPicture;
    }

    public Integer getMinNumberOfUsers() {
        return minNumberOfUsers;
    }

    public void setMinNumberOfUsers(Integer minNumberOfUsers) {
        this.minNumberOfUsers = minNumberOfUsers;
    }

    public Integer getMaxNumberOfUsers() {
        return maxNumberOfUsers;
    }

    public void setMaxNumberOfUsers(Integer maxNumberOfUsers) {
        this.maxNumberOfUsers = maxNumberOfUsers;
    }

    public Double getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(Double pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    public Date getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(Date availableFrom) {
        this.availableFrom = availableFrom;
    }

    public Date getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(Date availableTo) {
        this.availableTo = availableTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateDeleted() {
        return dateDeleted;
    }

    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Date getDateLastEdited() {
        return dateLastEdited;
    }

    public void setDateLastEdited(Date dateLastEdited) {
        this.dateLastEdited = dateLastEdited;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", user=" + user +
                ", country='" + country + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", categories=" + categories +
                ", feedbacks=" + feedbacks +
                ", reservations=" + reservations +
                ", offerPicture=" + offerPicture +
                ", minNumberOfUsers=" + minNumberOfUsers +
                ", maxNumberOfUsers=" + maxNumberOfUsers +
                ", pricePerPerson=" + pricePerPerson +
                ", availableFrom=" + availableFrom +
                ", availableTo=" + availableTo +
                ", description='" + description + '\'' +
                ", enabled=" + enabled +
                ", active=" + active +
                ", dateAdded=" + dateAdded +
                ", dateDeleted=" + dateDeleted +
                ", dateLastEdited=" + dateLastEdited +
                '}';
    }
}
