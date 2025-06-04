package com.example.boilerplate.openAI.controller;

import com.example.boilerplate.openAI.dto.ScriptToJsonDto;
import com.example.boilerplate.openAI.service.GptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/gpt")
public class GptController {

    private final GptService gptService;

    @Autowired
    public GptController(GptService gptService) {
        this.gptService = gptService;
    }

    @PostMapping("/script-to-json")
    public String getScriptToJson(@RequestBody ScriptToJsonDto body) {
        String script = body.getScript();
        return gptService.getScriptToJson(script);
    }

    @PostMapping("/chat")
    public String getTestChat(@RequestBody Map<String, String> body) {
        String prompt = body.get("prompt");
        return gptService.getChat(prompt);
    }
}
