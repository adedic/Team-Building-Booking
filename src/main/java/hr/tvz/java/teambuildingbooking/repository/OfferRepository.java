package hr.tvz.java.teambuildingbooking.repository;

import hr.tvz.java.teambuildingbooking.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findAll();

    @Query(value = "select t2.id, t2.active, t2.available_from, t2.available_to, t2.city, t2.country, t2.date_added, t2.date_deleted, t2.date_last_edited, t2.description, t2.enabled, t2.max_number_of_users, t2.min_number_of_users, t2.price_per_person, t2.offer_picture_id, t2.user_id\n" +
            "from feedback t1\n" +
            "join offer t2\n" +
            "on t1.offer_id = t2.id\n" +
            "where t2.active = true \n" +
            "and t2.enabled = true\n" +
            "and t2.date_deleted is null\n" +
            "and date(t2.available_from) <= current_date\n" +
            "and date(t2.available_to) >= current_date\n" +
            "and t1.number_of_stars != 1\n" +
            "group by t2.id\n" +
            "having avg(t1.number_of_stars) >= 4\n" +
            "order by avg(t1.number_of_stars) desc, t2.price_per_person\n" +
            "limit 9", nativeQuery = true)
    List<Offer> findTopOffers();
}
