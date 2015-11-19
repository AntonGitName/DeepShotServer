package ru.spbau.mit.antonpp.deepshot.server.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spbau.mit.antonpp.deepshot.server.database.model.MLOutputRecord;
import ru.spbau.mit.antonpp.deepshot.server.database.model.MLOutputRecord.Status;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/**
 * @author antonpp
 * @since 27/10/15
 */
@Repository
public class OutputRecordRepository {

    private static final String FIND_BY_ID = "SELECT * FROM MLOutputRecord WHERE id = ?";
    private static final String FIND_ALL_BY_USERNAME = "SELECT O.* FROM MLOutputRecord O JOIN UserInputRecord I ON I.id = O.userInputRecord WHERE I.username = ?";
    private static final String INSERT = "INSERT INTO MLOutputRecord(imageUrl, status, userInputRecord) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE MLOutputRecord SET status = ?, imageUrl = ? WHERE id = ?";

    private static final RowMapper<MLOutputRecord> filterMapper = (rs, rowNum) -> {
        MLOutputRecord MLOutputRecord = new MLOutputRecord();

        MLOutputRecord.setId(rs.getLong("id"));
        MLOutputRecord.setImageUrl(rs.getString("imageUrl"));
        MLOutputRecord.setStatus(Status.valueOf(rs.getString("status")));
        MLOutputRecord.setUserInputRecord(rs.getLong("userInputRecord"));

        return MLOutputRecord;
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
    public long addOutputRecord(Status status, String imageUrl, long inputRecordId) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, imageUrl);
            ps.setString(2, status.toString());
            ps.setLong(3, inputRecordId);
            return ps;
        }, keyHolder);
        return (Integer) keyHolder.getKeys().get("id");
    }

    @Transactional
    public void updateOutputRecord(long id, Status status, String imageUrl) {
        jdbc.update(UPDATE, status.toString(), imageUrl, id);
    }

}
