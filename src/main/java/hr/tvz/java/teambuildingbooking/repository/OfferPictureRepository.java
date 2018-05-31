package hr.tvz.java.teambuildingbooking.repository;

import hr.tvz.java.teambuildingbooking.model.OfferPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface OfferPictureRepository extends JpaRepository<OfferPicture, Long> {

    @Transactional
    @Modifying
    @Query("delete from OfferPicture op where op.id = ?1 ")
    void deleteById(Long id);
}
