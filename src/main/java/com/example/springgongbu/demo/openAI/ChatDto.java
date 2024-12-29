package com.example.springgongbu.demo.openAI;

import java.util.List;
import lombok.Data;

@Data
public class ChatDto {

    private List<Choice> choices;

    public String getContent() {
        return choices.get(0).message.content;
    }

    // 내부 Choice 클래스
    public static class Choice {
        private Message message;
    }

    public static class Message {
        private String content;
    }
}