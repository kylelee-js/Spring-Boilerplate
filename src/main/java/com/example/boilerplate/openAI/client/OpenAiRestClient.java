package com.example.boilerplate.openAI.client;

import com.example.boilerplate.openAI.dto.ChatRequestDto;

public interface OpenAiRestClient {
    public String getChat(ChatRequestDto requestBody);
}
