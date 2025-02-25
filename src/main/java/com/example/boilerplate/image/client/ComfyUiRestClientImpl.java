package com.example.boilerplate.image.client;

import com.example.boilerplate.image.dto.UploadDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class ComfyUiRestClientImpl implements ComfyUiRestClient {

    private final RestClient restClient;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public ComfyUiRestClientImpl() {
        log.info("ComfyUiRestClientImpl created.");
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8188")
                .build();
    }

    public UploadDto uploadImage(MultipartFile imageFile)  {
        try {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", new ByteArrayResource(imageFile.getBytes()) {
                @Override
                public String getFilename() {
                    return imageFile.getOriginalFilename();  // 파일명 추가
                }
            });

            UploadDto response = restClient.post()
                    .uri("/upload/image")
                    .body(body)
                    .retrieve()
                    .body(UploadDto.class);
            return response;

        } catch (IOException e) {
            log.error("Error uploading image: {}", e.getMessage());
            return null;
        }
    }

    public CompletableFuture<String> expression(String prompt, String clientId) {
        try {
            CompletableFuture<String> future = new CompletableFuture<>();
            String body = """
                {
                    "clientId": "%s",
                    "prompt": %s
                }
                """.formatted(clientId, prompt);

            String response = restClient.post()
                    .uri("/prompt")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .body(String.class);

            String promptId = new ObjectMapper().readTree(response).get("prompt_id").asText();
            log.info("Expression completed for prompt_id: {}", promptId);

            int retryCount = 0;
            int maxRetries = 5;
            JsonNode outputsNode = null;

            while (retryCount < maxRetries) {
                String output = restClient.get()
                        .uri("/history/%s".formatted(promptId))
                        .retrieve()
                        .body(String.class);
                log.info("Output: {}", output);

                try {
                    JsonNode rootNode = objectMapper.readTree(output);
                    JsonNode historyNode = rootNode.get(promptId);
                    if (historyNode != null && historyNode.has("outputs") && historyNode.get("outputs").has("33")) {
                        outputsNode = historyNode.get("outputs").get("33").get("images");
                        break;
                    }
                } catch (JsonProcessingException e) {
                    log.error("Error parsing output JSON: {}", e.getMessage());
                }

                retryCount++;
                log.warn("Outputs not found, retrying in 3 seconds... ({}/{})", retryCount, maxRetries);
                try {
                    Thread.sleep(3000); // 3초 대기 후 재시도
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("Thread interrupted during retry delay");
                    return null;
                }
            }

            if (outputsNode == null) {
                log.error("Failed to retrieve outputs after {} retries", maxRetries);
                return null;
            }

            for (JsonNode image : outputsNode) {
                String filename = image.get("filename").asText();
                future.complete(filename);
                log.info("Filename: {}", filename);
            }

            return future;
        } catch (IOException e) {
            log.error("Error sending prompt: {}", e.getMessage());
            return null;
        }
    }
}
