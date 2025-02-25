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

    @GetMapping("/script-form")
    public String getScriptForm() {
        return """
            <!DOCTYPE html>
            <html lang="ko">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Script to JSON</title>
                <script>
                    function sendScript() {
                        const scriptText = document.getElementById("script").value;

                        fetch("/gpt/script-to-json", {
                            method: "POST",
                            headers: {
                                "Content-Type": "application/json"
                            },
                            body: JSON.stringify({ script: scriptText })
                        })
                        .then(response => response.text())
                        .then(data => {
                            document.getElementById("result").textContent = data;
                        })
                        .catch(error => console.error("Error:", error));
                    }
                </script>
            </head>
            <body>
                <h2>Script to JSON 변환</h2>
                <textarea id="script" rows="10" cols="50" placeholder="여기에 스크립트를 입력하세요"></textarea>
                <br>
                <button onclick="sendScript()">전송</button>
                <h3>결과:</h3>
                <pre id="result"></pre>
            </body>
            </html>
            """;
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
