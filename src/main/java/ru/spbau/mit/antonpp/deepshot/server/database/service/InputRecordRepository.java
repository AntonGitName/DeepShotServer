package ru.spbau.mit.antonpp.deepshot.server.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spbau.mit.antonpp.deepshot.server.database.model.Style;
import ru.spbau.mit.antonpp.deepshot.server.database.model.UserInputRecord;

import java.sql.*;

/**
 * @author antonpp
 * @since 27/10/15
 */
@Repository
public class InputRecordRepository {

    private static final String FIND_BY_ID = "SELECT * FROM UserInputRecord WHERE id = ?";
    private static final String INSERT = "INSERT INTO UserInputRecord(username, style, imageUrl) VALUES (?, ?, ?)";

    private static final RowMapper<UserInputRecord> filterMapper = new RowMapper<UserInputRecord>() {
        @Override
        public UserInputRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserInputRecord userInputRecord = new UserInputRecord();
            userInputRecord.setId(rs.getLong("id"));
            userInputRecord.setUsername(rs.getString("username"));
            userInputRecord.setImageUrl(rs.getString("imageUrl"));
            userInputRecord.setStyle((Style) rs.getObject("style"));
            return userInputRecord;
        }
    };

    @Autowired
    protected JdbcTemplate jdbc;

    @Transactional
    public UserInputRecord getInputRecordById(long id) {
        return jdbc.queryForObject(FIND_BY_ID, new Object[]{id}, filterMapper);
    }

    @Transactional
    public long addInputRecord(final String username, final String imageUrl, final long styleId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, username);
                ps.setLong(2, styleId);
                ps.setString(3, imageUrl);
                return ps;
            }
        }, keyHolder);
//        return (Integer) keyHolder.getKeys().get("id");
        return (Integer) keyHolder.getKeys().get("last_insert_rowid()");
    }
}
