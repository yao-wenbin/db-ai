package io.github.yaowenbin.dbai.ai;

import io.github.yaowenbin.dbai.SpringContextTest;
import io.github.yaowenbin.dbai.ai.AiService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AiServiceTest extends SpringContextTest {

    @Resource
    AiService aiService;

    @Test
    void generate() {
        var prompt = "give me a joke";
        var res = aiService.generate(prompt);

        assertThat(res).isNotNull();

    }

}
