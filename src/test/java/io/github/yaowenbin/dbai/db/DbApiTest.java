package io.github.yaowenbin.dbai.db;

import com.alibaba.fastjson2.JSONObject;
import io.github.yaowenbin.dbai.SpringContextTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DbApiTest extends SpringContextTest {

    @Test
    void createDataSourceApi() throws Exception {
        String json =  JSONObject.of("key", "ds1", "url", "127.0.0.1", "username", "root", "password", "root", "schema", "test").toJSONString();

        mvc.perform(post("/datasources")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());

    }

}