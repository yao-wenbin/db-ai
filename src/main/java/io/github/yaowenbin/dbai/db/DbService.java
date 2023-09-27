package io.github.yaowenbin.dbai.db;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class DbService {

    public void createDataSource(DataSourceCreateRequest command) {
        HikariDataSource ds =
                command.convert();

        try (Connection conn = ds.getConnection()) {
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
