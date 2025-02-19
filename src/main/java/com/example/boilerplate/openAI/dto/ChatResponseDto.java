package com.example.boilerplate.openAI.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // 불필요한 필드 무시
public class ChatResponseDto {

    private String id;
    private String object;
    private long created;
    private String model;

    @JsonProperty("choices")
    private List<Choice> choices;

    @JsonProperty("usage")
    private Usage usage;

    public String getContent() {
        if (choices != null && !choices.isEmpty() && choices.get(0).message != null) {
            return choices.get(0).message.content;
        }
        return null;
    }

    @Data
    public static class Choice {
        private int index;
        private Message message;

        @JsonProperty("finish_reason")
        private String finishReason;
    }

    @Data
    public static class Message {
        private String role;
        private String content;
    }

    @Data
    public static class Usage {
        @JsonProperty("prompt_tokens")
        private int promptTokens;

        @JsonProperty("completion_tokens")
        private int completionTokens;

        @JsonProperty("total_tokens")
        private int totalTokens;
    }
}