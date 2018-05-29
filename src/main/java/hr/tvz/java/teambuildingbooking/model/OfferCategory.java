package hr.tvz.java.teambuildingbooking.model;

import javax.persistence.*;

@Entity
@Table(name = "OFFER_CATEGORY")
public class OfferCategory {

    @Id
    @Column(name = "OFFER_ID")
    private Long offerId;

    @Column(name = "CATEGORY_ID")
    private Long categoryId;

    public OfferCategory() {
        // default constructor
    }

    // --- get / set methods --------------------------------------------------

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
