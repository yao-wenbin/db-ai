package io.github.yaowenbin.dbai.db;

import com.alibaba.fastjson2.JSONObject;
import io.github.yaowenbin.commons.string.Strings;
import io.github.yaowenbin.dbai.SpringContextTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * integration test  for {@link DbApi}
 */

class DbApiTest extends SpringContextTest {

    @Test
    @Order(1)
    void createDataSourceApi() throws Exception {
        String json =  JSONObject.of("key", "ds1", "url", "127.0.0.1", "username", "root", "password", "root", "schema", "test").toJSONString();

        mvc.perform(post("/datasources")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @Order(2)
    void initializedDbApi() throws Exception {
        String json = JSONObject.of("sql", """
                USE TEST;
                drop table if exists user;
                create table user
                (
                    id       bigint auto_increment,
                    username varchar(255) default '' not null,
                    email    varchar(255) default '' not null,
                    password varchar(255) default '' not null,
                    address  varchar(255) default '' not null,
                    constraint user_pk
                        primary key (id)
                );
                drop table if exists product;
                create table product
                (
                    id       bigint auto_increment,
                    name varchar(255) default '' not null,
                    description    varchar(255) default '' not null,
                    price decimal(10, 2) default 0 not null,
                    constraint product_pk
                        primary key (id)
                );
                """).toJSONString();

        mvc.perform(post(Strings.format("/datasources/{}/initialize", "ds1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andDo(print())
            .andExpect(status().isOk());
    }

}