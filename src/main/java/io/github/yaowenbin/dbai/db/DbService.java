package io.github.yaowenbin.dbai.db;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class DbService {

    Map<String/* key */, HikariDataSource> dataSourceMap = new ConcurrentHashMap<>(16);

    public void createDataSource(DataSourceCreateRequest command) {
        HikariDataSource ds =
                command.convert();

        try (Connection conn = ds.getConnection()) {
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        dataSourceMap.put(command.getKey(), ds);
    }

}
