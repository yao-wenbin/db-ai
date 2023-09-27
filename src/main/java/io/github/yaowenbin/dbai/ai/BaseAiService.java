package io.github.yaowenbin.dbai.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

/**
 *
 * Chat GPT Request like this:
 * $ curl https://api.openai.com/v1/chat/completions \
 *   -H "Content-Type: application/json" \
 *   -H "Authorization: Bearer $OPENAI_API_KEY" \
 *   -d '{
 *     "model": "gpt-3.5-turbo",
 *     "messages": [{"role": "user", "content": "Hello!"}]
 *   }'
 *  a base Ai Service to load AI configuration item.
 *
 */
public class BaseAiService {



    protected final RestTemplate restTemplate;
    protected static final String AI_API = "https://api.openai.com/v1/chat/completions";
    protected static final String AI_MODEL = "gpt-3.5-turbo";

    @Value("${ai.key}")
    String aiKey;

    public BaseAiService() {
        this.restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + aiKey);
            return execution.execute(request, body);
        }));
    }

    public String generate(String prompt) {
        ChatRequest request = new ChatRequest(AI_MODEL, prompt);

        // call the API
        ChatResponse response = restTemplate.postForObject(AI_API, request, ChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        return response.getChoices().stream().findFirst().get().getMessage().getContent();
    }
}
