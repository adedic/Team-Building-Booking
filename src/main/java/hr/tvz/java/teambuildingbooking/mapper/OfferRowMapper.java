package hr.tvz.java.teambuildingbooking.mapper;

import hr.tvz.java.teambuildingbooking.model.Offer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OfferRowMapper implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Offer offer = new Offer();
        offer.setId(rs.getLong("ID"));
        return offer;
    }

}