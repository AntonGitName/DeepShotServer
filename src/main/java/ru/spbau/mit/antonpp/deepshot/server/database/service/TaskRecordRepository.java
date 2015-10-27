package ru.spbau.mit.antonpp.deepshot.server.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spbau.mit.antonpp.deepshot.server.database.model.Filter;
import ru.spbau.mit.antonpp.deepshot.server.database.model.TaskRecord;

/**
 * @author antonpp
 * @since 27/10/15
 */
@Repository
public class TaskRecordRepository {

    private static final String FIND_BY_ID = "SELECT * FROM TaskRecord WHERE id = ?";
    private static final String INSERT = "INSERT INTO TaskRecord(id, name, filter) VALUES (?, ?, ?);";
    private static final String GET_ALL = "SELECT * FROM TaskRecord";

    private static final RowMapper<TaskRecord> filterMapper = (rs, rowNum) -> {
        TaskRecord taskRecord = new TaskRecord();
        taskRecord.setId(rs.getLong("id"));
        taskRecord.setEncodedImage(rs.getString("encodedImage"));
        taskRecord.setFilter((Filter) rs.getObject("filter"));
        return taskRecord;
    };

    @Autowired
    protected JdbcTemplate jdbc;

    @Transactional
    public Iterable<TaskRecord> getTaskRecords() {
        return jdbc.query(GET_ALL, filterMapper);
    }

    @Transactional
    public TaskRecord getTaskRecordById(long id) {
        return jdbc.queryForObject(FIND_BY_ID, new Object[]{id}, filterMapper);
    }

    @Transactional
    public void addTaskRecord(long id, String encodedImage, long filter) {
        jdbc.update(INSERT, id, encodedImage, filter);
    }
}
