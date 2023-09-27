package io.github.yaowenbin.dbai.ai;


import org.springframework.stereotype.Service;

@Service
public class MockDbAiService extends DbAiService{

    @Override
    public String generate(String keyword) {
        return """
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
    }
}
