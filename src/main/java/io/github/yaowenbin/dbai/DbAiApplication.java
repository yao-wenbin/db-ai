package io.github.yaowenbin.dbai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DbAiApplication {
    public static void main(String... args) {
        SpringApplication.run(DbAiApplication.class, args);
    }
}
