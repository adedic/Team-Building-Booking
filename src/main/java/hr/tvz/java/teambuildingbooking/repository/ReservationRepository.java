package hr.tvz.java.teambuildingbooking.repository;

import hr.tvz.java.teambuildingbooking.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.dateOfReservation = :dateOfReservation AND r.offer.id = :offerId AND r.canceled = false")
    List<Reservation> getReservationsByOffer(@Param("dateOfReservation")Date dateOfReservation, @Param("offerId") Long offerId);

    List<Reservation> findAllByUser_Id(Long userId);

}
