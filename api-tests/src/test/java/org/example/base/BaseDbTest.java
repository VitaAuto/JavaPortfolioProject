package org.example.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@Slf4j
@Component
public class BaseDbTest {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public void clearTableAndAwait(String tableName) {
        clearTable(tableName);
        await()
                .atMost(5, TimeUnit.SECONDS)
                .pollInterval(200, TimeUnit.MILLISECONDS)
                .until(() -> countInDbTable(tableName) == 0);
        log.info("Rows in table {} after clear: {}", tableName, countInDbTable(tableName));
    }

    public void clearTable(String tableName) {
        if (!tableName.matches("\\w+")) {
            throw new IllegalArgumentException("Invalid table name: " + tableName);
        }
        var sqlClearStatement = String.format("DELETE FROM %s", tableName);
        jdbcTemplate.execute(sqlClearStatement);
        log.info("Table {} cleared", tableName);
    }

    public int countInDbTable(String tableName) {
        if (!tableName.matches("\\w+")) {
            throw new IllegalArgumentException("Invalid table name: " + tableName);
        }
        var sqlStatement = String.format("SELECT COUNT(*) FROM %s", tableName);
        var count = jdbcTemplate.queryForObject(sqlStatement, Integer.class);
        log.info("Orders count: {}", count);
        return count != null ? count : 0;
    }
}