package hr.tvz.java.teambuildingbooking.repository;

import hr.tvz.java.teambuildingbooking.model.OfferCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferCategoryRepository extends JpaRepository<OfferCategory, Long> {

    @Modifying
    @Query("delete from OfferCategory oc where oc.offerId = ?1")
    void deleteOfferCategories(Long offerId);
}
