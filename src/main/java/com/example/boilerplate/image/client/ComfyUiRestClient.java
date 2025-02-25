package com.example.boilerplate.image.client;

import com.example.boilerplate.image.dto.UploadDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface ComfyUiRestClient {
    public UploadDto uploadImage(MultipartFile imageFile);
    public CompletableFuture<String> expression(String prompt, String clientId);
}
