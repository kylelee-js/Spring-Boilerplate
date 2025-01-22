package com.example.boilerplate.openAI.service;

import com.example.boilerplate.openAI.dto.ChatDto;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class GptService {

    private final String apiKey;
    private final RestClient restClient;
    private final RestTemplate restTemplate;

    @Value("${OPENAI_API_KEY}")
    private String apiKey2;

    @Autowired
    public GptService(Dotenv dotenv, RestTemplate restTemplate, RestClient restClient) {
        this.apiKey = dotenv.get("OPENAI_API_KEY");
        this.restClient = restClient;
        this.restTemplate = restTemplate;
    }

    public String getChat2(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";

        Map requestBody = Map.of(
                "model", "gpt-4o-mini",
                "messages", List.of(
                        Map.of(
                                "role", "system",
                                "content", "You are a helpful assistant."
                        ),
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                )
        );

        ChatDto result = restClient.post().uri(url).headers(httpHeaders -> {
                    httpHeaders.set("Authorization", "Bearer " + apiKey);
                    httpHeaders.set("Content-Type", "application/json");
                }).contentType(MediaType.APPLICATION_JSON).body(requestBody)
                .retrieve().body(ChatDto.class);

        String text = result.getContent();
        return text;
    }

    public String getChat(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        Map requestBodyMap = Map.of(
                "model", "gpt-4o-mini",
                "messages", List.of(
                        Map.of(
                                "role", "system",
                                "content", "You are a helpful assistant."
                        ),
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                )
        );

        HttpEntity<Map> entity = new HttpEntity<>(requestBodyMap, headers);
        ChatDto response = restTemplate.postForObject(url, entity, ChatDto.class);
        String text = response.getContent();
        return text;
    }
}
