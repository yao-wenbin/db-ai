package io.github.yaowenbin.dbai.ai;

import org.springframework.stereotype.Service;


@Service
public class DefaultAiService extends BaseAiService {

    @Override
    public String generate(String prompt) {
        ChatRequest request = new ChatRequest(AI_MODEL, prompt);

        // call the API
        ChatResponse response = restTemplate.postForObject(AI_API, request, ChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        return response.getChoices().stream().findFirst().get()
                .getMessage().getContent();
    }

}
