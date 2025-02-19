package com.example.boilerplate.openAI.client;

import com.example.boilerplate.openAI.dto.ChatRequestDto;
import com.example.boilerplate.openAI.dto.ChatResponseDto;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class OpenAiRestClientImpl implements OpenAiRestClient {

    private final RestClient restClient;

    public OpenAiRestClientImpl(Dotenv dotenv) {
        String apiKey = dotenv.get("OPENAI_API_KEY");
        log.info("OpenAiRestClientImpl created: apiKey={}", apiKey);
        this.restClient = RestClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("Authorization", "Bearer " + apiKey);
                    httpHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                })
                .build();
    }

    @Override
    public String getChat(ChatRequestDto requestBody) {
        ChatResponseDto response = restClient.post()
                .body(requestBody)
                .retrieve()
                .body(ChatResponseDto.class);

        return response != null ? response.getContent() : "";
    }
}
