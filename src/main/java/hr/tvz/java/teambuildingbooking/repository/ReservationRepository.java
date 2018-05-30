package hr.tvz.java.teambuildingbooking.repository;

import hr.tvz.java.teambuildingbooking.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {



}
