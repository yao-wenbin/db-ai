package io.github.yaowenbin.dbai.ai;

import io.github.yaowenbin.dbai.common.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AiApi {

    private final DbAiService dbAiService;

    @PostMapping("/ai/generate")
    public R<String> generate(@RequestBody AiGenerateRequest request) {
        return R.success(dbAiService.generate(request.getPrompt()));
    }

}