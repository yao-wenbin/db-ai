package io.github.yaowenbin.dbai.db;

import com.zaxxer.hikari.HikariDataSource;
import io.github.yaowenbin.dbai.SpringContextTest;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DbInitializerTest extends SpringContextTest {

    @Resource
    DbInitializer dbInitializer;

    HikariDataSource ds = new HikariDataSource();

    @BeforeEach
    void setUp() {
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setSchema("test");
        ds.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test");
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
    }

    @Test
    void initialize() throws FileNotFoundException, SQLException {
        ClassPathResource resource = new ClassPathResource("schema.sql");

        dbInitializer.initialize(ds, resource);

        assertTableExists(ds, List.of("user", "product"));
    }

    @Test
    void checkSchemaExists_whenSchemaUnExists() {
        Assertions.assertDoesNotThrow(() -> {
            dbInitializer.makeSureSchemaExists(ds);
        });
    }

    private void assertTableExists(HikariDataSource dataSource, List<String> tables) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("schema", "test").addValue("tables", tables);

        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        Integer counts = jdbcTemplate.queryForObject("""
                         SELECT COUNT(TABLE_NAME) 
                         FROM information_schema.TABLES
                         WHERE TABLE_SCHEMA = :schema
                         AND TABLE_NAME IN (:tables)
         """, params, Integer.class);

        assertThat(counts).isEqualTo(tables.size());
    }

}
