package io.github.yaowenbin.dbai.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties("ai")
@Configuration
public class AiProperties {

    private String key;

    private boolean mock = false;

}
