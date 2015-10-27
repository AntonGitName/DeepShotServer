package ru.spbau.mit.antonpp.deepshot.server.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spbau.mit.antonpp.deepshot.server.database.model.TaskRecord;
import ru.spbau.mit.antonpp.deepshot.server.database.model.TaskResult;

/**
 * @author antonpp
 * @since 27/10/15
 */
@Repository
public class TaskResultRepository {

    private static final String FIND_BY_ID = "SELECT * FROM TaskRecord WHERE id = ?";
    private static final String INSERT = "INSERT INTO TaskResult(encodedImage, taskRecord) VALUES (?, ?);";

    private static final RowMapper<TaskResult> filterMapper = (rs, rowNum) -> {
        TaskResult taskResult = new TaskResult();
        taskResult.setId(rs.getLong("id"));
        taskResult.setTaskRecord((TaskRecord) rs.getObject("taskRecord"));
        taskResult.setEncodedImage(rs.getString("encodedImage"));
        return taskResult;
    };

    @Autowired
    protected JdbcTemplate jdbc;

    @Transactional
    public TaskResult getTaskRecordById(long id) {
        return jdbc.queryForObject(FIND_BY_ID, new Object[]{id}, filterMapper);
    }

    @Transactional
    public void addTaskResult(String encodedImage, long taskId) {
        jdbc.update(INSERT, encodedImage, taskId);
    }

}
