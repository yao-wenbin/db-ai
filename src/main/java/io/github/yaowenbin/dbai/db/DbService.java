package io.github.yaowenbin.dbai.db;

import com.zaxxer.hikari.HikariDataSource;
import io.github.yaowenbin.commons.file.Files;
import io.github.yaowenbin.commons.string.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class DbService {

    private final DbInitializer initializer;

    Map<String/* key */, HikariDataSource> dataSourceMap = new ConcurrentHashMap<>(16);

    public void createDataSource(DataSourceCreateRequest command) {
        HikariDataSource ds =
                command.convert();

        try (Connection ignored = ds.getConnection()) {
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ds = replaceJdbcUrlWithDefaultSchema(ds);

        dataSourceMap.put(command.getKey(), ds);
    }

    private HikariDataSource replaceJdbcUrlWithDefaultSchema(HikariDataSource ds) {
        String schema = ds.getSchema();
        try {
            initializer.makeSureSchemaExists(ds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String newJdbcUrlWithDefaultSchema = Strings.format("{}/{}", ds.getJdbcUrl(), schema);

        HikariDataSource newDs = new HikariDataSource();
        newDs.setJdbcUrl(newJdbcUrlWithDefaultSchema);
        newDs.setUsername(ds.getUsername());
        newDs.setPassword(ds.getPassword());
        newDs.setSchema(ds.getSchema());

        return newDs;
    }

    public void initialize(String dsKey, String sql){
        HikariDataSource ds = Optional.ofNullable(dataSourceMap.get(dsKey))
                .orElseThrow(() -> new RuntimeException(Strings.format("please create a datasource using /datasources api with key", dsKey)));

        Resource resource = convertToResource(sql);

        initializer.initialize(ds, resource);
    }

    public Resource convertToResource(String sql) {
        Path sqlPath;
        try {
            sqlPath = Files.writeString(Path.of("./tmp.sql"), sql);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new FileSystemResource(sqlPath);
    }
}
