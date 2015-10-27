package ru.spbau.mit.antonpp.deepshot.server.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spbau.mit.antonpp.deepshot.server.database.model.Filter;

import java.util.List;

/**
 * @author antonpp
 * @since 26/10/15
 */
@Repository
public class FilterRepository {

    private static final String GET_ALL = "SELECT * FROM Filter";
    private static final String GET_ALL_NAMES = "SELECT name FROM Filter";
    private static final String GET_BY_NAME = "SELECT * FROM Filter WHERE name = ?";

    private static final RowMapper<Filter> filterMapper = (rs, rowNum) -> {
        Filter filter = new Filter();
        filter.setId(rs.getLong("id"));
        filter.setName(rs.getString("name"));
        filter.setPreview(rs.getString("preview"));
        return filter;
    };

    private static final RowMapper<String> namesMapper = (rs, rowNum) -> rs.getString("name");

    @Autowired
    protected JdbcTemplate jdbc;

    @Transactional
    public Iterable<Filter> getFilters() {
        return jdbc.query(GET_ALL, filterMapper);
    }

    @Transactional
    public List<String> getFilterNames() {
        return jdbc.query(GET_ALL_NAMES, namesMapper);
    }

    @Transactional
    public Filter getFilterByName(String name) {
        return jdbc.queryForObject(GET_BY_NAME, new Object[]{name}, filterMapper);
    }
}
