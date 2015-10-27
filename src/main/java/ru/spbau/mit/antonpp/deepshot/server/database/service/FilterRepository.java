package ru.spbau.mit.antonpp.deepshot.server.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spbau.mit.antonpp.deepshot.server.database.model.Filter;

/**
 * @author antonpp
 * @since 26/10/15
 */
@Repository
public class FilterRepository {

    private static final String GET_ALL = "SELECT * FROM Filter";

    private static final RowMapper<Filter> filterMapper = (rs, rowNum) -> {
        Filter filter = new Filter();
        filter.setId(rs.getLong("id"));
        filter.setName(rs.getString("name"));
        return filter;
    };

    @Autowired
    protected JdbcTemplate jdbc;

    @Transactional
    public Iterable<Filter> getFilters() {
        return jdbc.query(GET_ALL, filterMapper);
    }
}
