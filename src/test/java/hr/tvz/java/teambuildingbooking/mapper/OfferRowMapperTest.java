package hr.tvz.java.teambuildingbooking.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferRowMapperTest {
    @MockBean
    OfferRowMapper offerRowMapper;

    @Test
    public void mapRow() throws SQLException {
        ResultSet rs = null;
        int rowNum = 1;
        Mockito.when(offerRowMapper.mapRow(rs, rowNum)).thenReturn(new Object());
    }
}