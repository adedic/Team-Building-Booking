package hr.tvz.java.teambuildingbooking.controller;

import hr.tvz.java.teambuildingbooking.mapper.OfferRowMapper;
import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/speedTest")
public class DatabaseTestController {

    private final JdbcTemplate jdbcTemplate;

    private final OfferService offerService;

    @Autowired
    public DatabaseTestController(JdbcTemplate jdbcTemplate, OfferService offerService) {
        this.jdbcTemplate = jdbcTemplate;
        this.offerService = offerService;
    }

    @ResponseBody
    @GetMapping(path = "/jdbc/offer/{id}", produces = "application/json")
    private String jdbcTest(@PathVariable("id") Long id) {
        Date dateStarted = new Date();
        String sql = "SELECT * FROM OFFER WHERE ID = ?";

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(((Offer) jdbcTemplate.queryForObject(sql, new Object[]{id}, new OfferRowMapper())).toString());
        Date dateEnded = new Date();

        stringBuilder.append("\n\nTime elapsed (ms):" + getDateDiff(dateStarted, dateEnded, TimeUnit.MILLISECONDS));

        return stringBuilder.toString();
    }

    @ResponseBody
    @GetMapping(path = "/jpa/offer/{id}", produces = "application/json")
    private String jpaTest(@PathVariable("id") Long id) {
        Date dateStarted = new Date();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(offerService.findOne(id).toString());

        Date dateEnded = new Date();

        stringBuilder.append("\n\nTime elapsed (ms):" + getDateDiff(dateStarted, dateEnded, TimeUnit.MILLISECONDS));

        return stringBuilder.toString();
    }

    private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
