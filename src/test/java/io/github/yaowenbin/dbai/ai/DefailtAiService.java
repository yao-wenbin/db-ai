package io.github.yaowenbin.dbai.ai;

import io.github.yaowenbin.dbai.SpringContextTest;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefailtAiService extends SpringContextTest {

    @Resource
    DefaultAiService aiService;

    @Test
    void generate() {
        var prompt = "give me a joke";
        var res = aiService.generate(prompt);

        assertThat(res).isNotNull();

    }

}
