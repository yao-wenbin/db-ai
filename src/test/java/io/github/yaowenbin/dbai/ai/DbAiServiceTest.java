package io.github.yaowenbin.dbai.ai;

import io.github.yaowenbin.dbai.SpringContextTest;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class DbAiServiceTest extends SpringContextTest {

    @Resource
    DbAiService dbAiService;

    @Test
    void loadPrompt() {

    }

    @Test
    void generate() {
        dbAiService.generate("a shopping app; user can buy some product and then place an order in app; and app will have shipping service that can ship user's product to their address, and marked order status into completed");
    }

    @Test
    void storeToFile() throws IOException {
        String prompt = """
                CREATE TABLE users (
                    user_id INT PRIMARY KEY AUTO_INCREMENT,
                    username VARCHAR(255) NOT NULL,
                    email VARCHAR(255) NOT NULL,
                    password VARCHAR(255) NOT NULL,
                    address VARCHAR(255)
                );
                                
                CREATE TABLE products (
                    product_id INT PRIMARY KEY AUTO_INCREMENT,
                    product_name VARCHAR(255) NOT NULL,
                    description TEXT,
                    price DECIMAL(10, 2) NOT NULL
                );
                                
                CREATE TABLE orders (
                    order_id INT PRIMARY KEY AUTO_INCREMENT,
                    user_id INT NOT NULL,
                    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    status VARCHAR(20) DEFAULT 'Pending',
                    FOREIGN KEY (user_id) REFERENCES users(user_id)
                );
                                
                CREATE TABLE order_items (
                    order_item_id INT PRIMARY KEY AUTO_INCREMENT,
                    order_id INT NOT NULL,
                    product_id INT NOT NULL,
                    quantity INT NOT NULL,
                    FOREIGN KEY (order_id) REFERENCES orders(order_id),
                    FOREIGN KEY (product_id) REFERENCES products(product_id)
                );
                                
                CREATE TABLE shipping_info (
                    shipping_id INT PRIMARY KEY AUTO_INCREMENT,
                    order_id INT NOT NULL,
                    address VARCHAR(255) NOT NULL,
                    shipped_date TIMESTAMP,
                    status VARCHAR(20) DEFAULT 'Pending',
                    FOREIGN KEY (order_id) REFERENCES orders(order_id)
                );
                """;
        String sqlFile = dbAiService.storeToFile(prompt);

        try (BufferedReader reader = new BufferedReader(new FileReader(sqlFile))) {
            String res = reader.lines().collect(Collectors.joining());
            System.out.println(res);

            assertThat(StringUtils.trimAllWhitespace(res)).isEqualTo(StringUtils.trimAllWhitespace(prompt));
        }
    }
}