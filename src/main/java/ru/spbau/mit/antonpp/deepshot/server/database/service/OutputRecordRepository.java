package ru.spbau.mit.antonpp.deepshot.server.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spbau.mit.antonpp.deepshot.server.database.model.MLOutputRecord;
import ru.spbau.mit.antonpp.deepshot.server.database.model.MLOutputRecord.Status;

import java.sql.*;
import java.util.List;

/**
 * @author antonpp
 * @since 27/10/15
 */
@Repository
public class OutputRecordRepository {

    private static final String FIND_BY_ID = "SELECT * FROM MLOutputRecord WHERE id = ?";
    private static final String FIND_OWNER_BY_ID = "SELECT U.* FROM UserInputRecord U JOIN MLOutputRecord ML ON ML.userInputRecord = U.id WHERE ML.id = ?";
    private static final String FIND_ALL_BY_USERNAME = "SELECT O.* FROM MLOutputRecord O JOIN UserInputRecord I ON I.id = O.userInputRecord WHERE I.username = ?";
    private static final String INSERT = "INSERT INTO MLOutputRecord(imageUrl, status, userInputRecord) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE MLOutputRecord SET status = ?, imageUrl = ? WHERE id = ?";

    private static final RowMapper<MLOutputRecord> filterMapper = new RowMapper<MLOutputRecord>() {
        @Override
        public MLOutputRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
            MLOutputRecord MLOutputRecord = new MLOutputRecord();

            MLOutputRecord.setId(rs.getLong("id"));
            MLOutputRecord.setImageUrl(rs.getString("imageUrl"));
            MLOutputRecord.setStatus(Status.valueOf(rs.getString("status")));
            MLOutputRecord.setUserInputRecord(rs.getLong("userInputRecord"));

            return MLOutputRecord;
        }
    };

    private static final RowMapper<String> ownerMapper = new RowMapper<String>() {
        @Override
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("username");
        }
    };

    @Autowired
    protected JdbcTemplate jdbc;

    @Transactional
    public List<MLOutputRecord> getAllRecordsByName(String username) {
        return jdbc.query(FIND_ALL_BY_USERNAME, new Object[]{username}, filterMapper);
    }

    @Transactional
    public MLOutputRecord getTaskRecordById(long id) {
        return jdbc.queryForObject(FIND_BY_ID, new Object[]{id}, filterMapper);
    }

    @Transactional
    public String getOwnerById(long id) {
        return jdbc.queryForObject(FIND_OWNER_BY_ID, new Object[]{id}, ownerMapper);
    }

    @Transactional
    public long addOutputRecord(final Status status, final String imageUrl, final long inputRecordId) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, imageUrl);
                ps.setString(2, status.toString());
                ps.setLong(3, inputRecordId);
                return ps;
            }
        }, keyHolder);
//        return (Integer) keyHolder.getKeys().get("id");
        return (Integer) keyHolder.getKeys().get("last_insert_rowid()");
    }

    @Transactional
    public void updateOutputRecord(long id, Status status, String imageUrl) {
        jdbc.update(UPDATE, status.toString(), imageUrl, id);
    }

}
