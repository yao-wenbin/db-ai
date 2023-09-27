package io.github.yaowenbin.dbai.ai.config;

import io.github.yaowenbin.commons.string.Strings;
import io.github.yaowenbin.dbai.ai.BaseAiService;
import io.github.yaowenbin.dbai.ai.DbAiService;
import io.github.yaowenbin.dbai.ai.MockDbAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AiAutoConfig {

    private final AiProperties properties;

    @Bean
    public BaseAiService aiService() {
        if (Strings.isBlank(properties.getKey()) || properties.isMock()) {
            log.warn("you dont have set a ai.key to configuration or open the mock mode, so ai service running mock mode");
            return new MockDbAiService();
        } else {
            return new DbAiService();
        }
    }

}
