package io.github.yaowenbin.dbai.db;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.SQLException;

@Service
public class DbInitializer {

    public void initialize(HikariDataSource ds, File schemaSql) throws SQLException {
        FileSystemResource resource = new FileSystemResource(schemaSql);
        ScriptUtils.executeSqlScript(ds.getConnection(), resource);
    }

}
