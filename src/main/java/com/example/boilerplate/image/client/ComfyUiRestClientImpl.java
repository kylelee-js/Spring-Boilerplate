package com.example.boilerplate.image.client;

import com.example.boilerplate.image.dto.UploadDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${comfyui.base-url}")
    private String comfyUiBaseUrl;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public ComfyUiRestClientImpl() {
        log.info("ComfyUiRestClientImpl created with baseUrl: {}", comfyUiBaseUrl);
        this.restClient = RestClient.builder()
                .baseUrl(comfyUiBaseUrl)
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

            return restClient.post()
                    .uri("/upload/image")
                    .body(body)
                    .retrieve()
                    .body(UploadDto.class);
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

                String outputs = "outputs";
                JsonNode rootNode = objectMapper.readTree(output);
                JsonNode historyNode = rootNode.get(promptId);
                if (historyNode != null && historyNode.has(outputs) && historyNode.get(outputs).has("33")) {
                    outputsNode = historyNode.get(outputs).get("33").get("images");
                    break;
                }

                retryCount++;
                log.warn("Outputs not found, retrying in 3 seconds... ({}/{})", retryCount, maxRetries);
                sleep(3 * 1000); // 3초 대기 후 재시도
            }

            if (outputsNode == null) {
                log.error("Failed to retrieve outputs after {} retries", maxRetries);
                return null;
            }

            for (JsonNode image : outputsNode) {
                String filename = image.get("filename").asText();
                future.complete(comfyUiBaseUrl + "/view?filename=" + filename);
                log.info("Filename: {}", filename);
            }

            return future;
        } catch (IOException e) {
            log.error("Error sending prompt: {}", e.getMessage());
            return null;
        }
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread interrupted during retry delay");
        }
    }
}
