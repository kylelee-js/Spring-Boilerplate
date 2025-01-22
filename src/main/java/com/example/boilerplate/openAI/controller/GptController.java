package com.example.boilerplate.openAI.controller;

import com.example.boilerplate.openAI.service.GptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GptController {

    private final GptService gptService;

    @Autowired
    public GptController(GptService gptService) {
        this.gptService = gptService;
    }

    @GetMapping("/gpt/test/chat")
    public String getTestChat() {
        String testPrompt = "김치찌개 만드는 방법";
        return gptService.getChat(testPrompt);
    }

    @GetMapping("/gpt/test/chat2")
    public String getTestChat2() {
        String testPrompt = "김치찌개 만드는 방법";
        return gptService.getChat2(testPrompt);
    }
}
