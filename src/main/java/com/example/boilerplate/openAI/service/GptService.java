package com.example.boilerplate.openAI.service;

import com.example.boilerplate.openAI.client.OpenAiRestClient;
import com.example.boilerplate.openAI.dto.ChatRequestDto;
import com.example.boilerplate.tools.Prompt;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GptService {
    private final OpenAiRestClient openAiRestClient;

    @Autowired
    public GptService(OpenAiRestClient openAiRestClient) {
        this.openAiRestClient = openAiRestClient;
    }

    public String getScriptToJson(String script) {
        ChatRequestDto requestDto = new ChatRequestDto(
                "gpt-4o-mini",
                List.of(
                        Prompt.Companion.scriptToJson(),
                        new ChatRequestDto.MessageDto("user", script)
                )
        );

        return openAiRestClient.getChat(requestDto);
    }

    public String getChat(String prompt) {
        log.info("getChat called with prompt: {}", prompt);
        ChatRequestDto requestDto = new ChatRequestDto(
                "gpt-4o-mini",
                List.of(
                        new ChatRequestDto.MessageDto("system", "You are a helpful assistant."),
                        new ChatRequestDto.MessageDto("user", prompt)
                )
        );

        log.info("Generated ChatRequestDto: {}", requestDto);

        return openAiRestClient.getChat(requestDto);
    }
}
