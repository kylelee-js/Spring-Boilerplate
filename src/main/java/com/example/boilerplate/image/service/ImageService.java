package com.example.boilerplate.image.service;

import com.example.boilerplate.image.client.ComfyUiRestClient;
import com.example.boilerplate.image.dto.UploadDto;
import com.example.boilerplate.tools.Emotion;
import com.example.boilerplate.tools.Prompt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ImageService {

    private final ComfyUiRestClient comfyUiRestClient;

    public ImageService(ComfyUiRestClient comfyUiRestClient) {
        this.comfyUiRestClient = comfyUiRestClient;
    }

    public String uploadImage(MultipartFile imageFile) {
        log.info("ImageService.uploadImage() called.");

        UploadDto response = comfyUiRestClient.uploadImage(imageFile);
        String url = response.getName();
        return url;
    }


    public CompletableFuture<String> expression(String emotion, MultipartFile imageFile) {
        log.info("ImageService.expression() called.");

        String imageUrl = uploadImage(imageFile);

        String outputUrl = "exp_" + imageUrl;

        Emotion emotionEnum;

        switch (emotion) {
            case "happy":
                emotionEnum = Emotion.HAPPY;
                break;
            case "sad":
                emotionEnum = Emotion.SAD;
                break;
            default:
                emotionEnum = Emotion.DEFAULT;
                break;
        }

        String prompt = Prompt.Companion.experssion(emotionEnum, imageUrl, outputUrl);

        String clientId = UUID.randomUUID().toString();

        return comfyUiRestClient.expression(prompt, clientId).thenApply(imageData ->
             "http://localhost:8188/view?filename=" + imageData
        );
    }
}
