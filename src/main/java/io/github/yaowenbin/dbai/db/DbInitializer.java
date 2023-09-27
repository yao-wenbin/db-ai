package io.github.yaowenbin.dbai.db;

import com.zaxxer.hikari.HikariDataSource;
import io.github.yaowenbin.commons.string.Strings;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class DbInitializer {

    public void initialize(HikariDataSource ds, Resource resource) {
        try {
            makeSureSchemaExists(ds);

            ScriptUtils.executeSqlScript(ds.getConnection(), resource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeSureSchemaExists(HikariDataSource ds) throws SQLException {
        if (Strings.isBlank(ds.getSchema()))
            throw new SQLException("schema of datasource cannot be null");

        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(Strings.format("create database if not exists {};", ds.getSchema()));
            ps.execute();
        }
    }
}
