package ru.spbau.mit.antonpp.deepshot.server.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spbau.mit.antonpp.deepshot.server.database.model.Style;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author antonpp
 * @since 26/10/15
 */
@Repository
public class StyleRepository {

    private static final String GET_ALL_ID = "SELECT id FROM Style";
    private static final String GET_BY_ID = "SELECT * FROM Style WHERE id = ?";

    private static final RowMapper<Style> filterMapper = new RowMapper<Style>() {
        @Override
        public Style mapRow(ResultSet rs, int rowNum) throws SQLException {
            Style style = new Style();
            style.setId(rs.getLong("id"));
            style.setName(rs.getString("name"));
            style.setUri(rs.getString("imageUrl"));
            return style;
        }
    };

    private static final RowMapper<Long> idsMapper = new RowMapper<Long>() {
        @Override
        public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getLong("id");
        }
    };

    @Autowired
    protected JdbcTemplate jdbc;

    @Transactional
    public List<Long> getStyleIds() {
        return jdbc.query(GET_ALL_ID, idsMapper);
    }

    @Transactional
    public Style getStyleById(Long id) {
        return jdbc.queryForObject(GET_BY_ID, new Object[]{id}, filterMapper);
    }
}
