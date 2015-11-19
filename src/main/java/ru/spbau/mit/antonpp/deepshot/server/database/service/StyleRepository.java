package ru.spbau.mit.antonpp.deepshot.server.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spbau.mit.antonpp.deepshot.server.database.model.Style;

import java.util.List;

/**
 * @author antonpp
 * @since 26/10/15
 */
@Repository
public class StyleRepository {

    private static final String GET_ALL_ID = "SELECT id FROM Style";
    private static final String GET_BY_ID = "SELECT * FROM Style WHERE id = ?";

    private static final RowMapper<Style> filterMapper = (rs, rowNum) -> {
        Style style = new Style();
        style.setId(rs.getLong("id"));
        style.setName(rs.getString("name"));
        style.setUri(rs.getString("imageUrl"));
        return style;
    };

    private static final RowMapper<Long> idsMapper = (rs, rowNum) -> rs.getLong("id");

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
