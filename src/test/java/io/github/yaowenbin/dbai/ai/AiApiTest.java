package io.github.yaowenbin.dbai.ai;

import com.alibaba.fastjson2.JSONObject;
import io.github.yaowenbin.dbai.SpringContextTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AiApiTest extends SpringContextTest {

    @Test
    void generate() throws Exception {
        String json = JSONObject.of("prompt", "a shopping app; user can buy some product and then place an order in app; and app will have shipping service that can ship user's product to their address, and marked order status into completed").toJSONString();

        mvc.perform(post("/ai/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }



}