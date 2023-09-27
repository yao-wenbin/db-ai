package io.github.yaowenbin.dbai;

import com.alibaba.fastjson2.JSONObject;
import io.github.yaowenbin.commons.string.Strings;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class EndToEndTest extends SpringContextTest{

    /**
     * Test Case 1: user using AI to generate schema sql and then initialize datasource.
     */

    final String prompt = "a shopping app; user can buy some product and then place an order in app; and app will have shipping service that can ship user's product to their address, and marked order status into completed";

    final String dbUsername = "root";
    final String dbPassword = "root";
    final String dbUrl = "127.0.0.1:3306";
    final String schema = "test";

    @Test
    void testcase1() throws Exception {
        String json = JSONObject.of("prompt", prompt).toJSONString();

        MvcResult result = mvc.perform(post("/ai/generate").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
        String sql = (String) JSONObject.parseObject(result.getResponse().getContentAsString()).get("data");

        json =  JSONObject.of("key", "ds1", "url", dbUrl, "username", dbUsername, "password", dbPassword, "schema", schema).toJSONString();

        mvc.perform(post("/datasources")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        json = JSONObject.of("sql", sql).toJSONString();

        mvc.perform(post(Strings.format("/datasources/{}/initialize", "ds1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        log.info("database and tables create success for in mysql server url: {} 's schema: {}", dbUrl, schema);
    }


}
