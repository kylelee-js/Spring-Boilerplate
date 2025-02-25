package com.example.boilerplate.image.controller;

import com.example.boilerplate.image.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/image")
public class ImageController {

    private static final String UPLOAD_DIR = "uploads/";
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/expression-form")
    public String expressionForm() {
        return """
        <!DOCTYPE html>
        <html lang="ko">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>이미지 감정 변환</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    max-width: 600px;
                    margin: auto;
                    padding: 20px;
                }
                h2 {
                    text-align: center;
                }
                .preview {
                    margin-top: 20px;
                    text-align: center;
                }
                .preview img {
                    max-width: 100%;
                    height: auto;
                    display: none;
                }
                .download-btn {
                    margin-top: 10px;
                    display: none;
                    text-align: center;
                }
                .download-btn a {
                    display: inline-block;
                    padding: 10px 15px;
                    background-color: #007bff;
                    color: white;
                    text-decoration: none;
                    border-radius: 5px;
                    font-size: 14px;
                }
            </style>
        </head>
        <body>

            <h2>이미지 감정 변환</h2>

            <form id="uploadForm">
                <input type="file" id="imageFile" name="image" accept="image/*" required><br><br>

                <label><input type="radio" name="emotion" value="default" checked> Default</label>
                <label><input type="radio" name="emotion" value="happy"> Happy</label>
                <label><input type="radio" name="emotion" value="sad"> Sad</label><br><br>

                <button type="submit">변환하기</button>
            </form>

            <div class="preview">
                <h3>변환된 이미지 미리보기</h3>
                <img id="previewImage" alt="변환된 이미지">
            </div>

            <div class="download-btn" id="downloadContainer">
                <a id="downloadLink" href="#" onclick="imgDownload(event, this.src, 'download.png')">이미지 다운로드</a>
            </div>

            <script>
                document.getElementById("uploadForm").addEventListener("submit", async function(event) {
                    event.preventDefault(); // 기본 제출 방지

                    const fileInput = document.getElementById("imageFile");
                    if (!fileInput.files.length) {
                        alert("이미지를 선택해주세요.");
                        return;
                    }

                    const formData = new FormData();
                    formData.append("image", fileInput.files[0]);

                    const selectedEmotion = document.querySelector('input[name="emotion"]:checked').value;
                    formData.append("emotion", selectedEmotion);

                    try {
                        const response = await fetch("/image/expression", {
                            method: "POST",
                            body: formData
                        });

                        if (!response.ok) {
                            throw new Error("서버 응답이 올바르지 않습니다.");
                        }

                        const imageUrl = await response.text();

                        // 미리보기 이미지 설정
                        const previewImage = document.getElementById("previewImage");
                        previewImage.src = imageUrl;
                        previewImage.style.display = "block";

                        // 다운로드 버튼 설정
                        const downloadContainer = document.getElementById("downloadContainer");
                        const downloadLink = document.getElementById("downloadLink");
                        downloadLink.href = imageUrl;
                        downloadContainer.style.display = "block";
                    } catch (error) {
                        alert("이미지 변환 중 오류 발생: " + error.message);
                    }
                });
                function imgDownload(event, imageURL, fileName) {
                    event.preventDefault();
                    var img = new Image();
                    	img.crossOrigin = "Anonymous";
                        img.id = "getshot";
                        img.src = imageURL;
                        document.body.appendChild(img);
                    var a = document.createElement("a");
                        a.href = getshot.src;
                        a.download = fileName;
                        a.click();
                        document.body.removeChild(img);
                }
            </script>

        </body>
        </html>
        """;
    }

    @PostMapping("/expression")
    public String expression(@RequestParam("image") MultipartFile imageFile, @RequestParam("emotion") String emotion) {
        if (imageFile.isEmpty()) return "파일이 비어 있습니다.";
        return imageService.expression(emotion, imageFile).join();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile imageFile) {
        if (imageFile.isEmpty()) return ResponseEntity.badRequest().body("파일이 비어 있습니다.");

        return ResponseEntity.ok(imageService.uploadImage(imageFile));
    }
}
