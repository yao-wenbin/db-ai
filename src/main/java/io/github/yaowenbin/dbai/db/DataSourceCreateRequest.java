package io.github.yaowenbin.dbai.db;

import com.zaxxer.hikari.HikariDataSource;
import io.github.yaowenbin.commons.string.Strings;
import lombok.Data;

@Data
public class DataSourceCreateRequest {

    private String key;

    private String username;

    private String password;

    private String url;

    private String schema;

    public HikariDataSource convert () {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(buildJdbcUrl());
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }

    public String buildJdbcUrl() {
        return Strings.format("jdbc:mysql://{}/{}", url, schema);
    }

}
