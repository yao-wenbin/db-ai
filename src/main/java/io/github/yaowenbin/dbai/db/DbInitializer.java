package io.github.yaowenbin.dbai.db;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class DbInitializer {

    public void initialize(HikariDataSource ds, Resource resource) throws SQLException {
        ScriptUtils.executeSqlScript(ds.getConnection(), resource);
    }

}
