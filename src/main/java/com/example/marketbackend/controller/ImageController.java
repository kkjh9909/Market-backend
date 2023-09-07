package com.example.marketbackend.controller;

import com.example.marketbackend.dto.image.ImageUrlResponse;
import com.example.marketbackend.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageUploadService imageUploadService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImageFile(@RequestParam("file") MultipartFile file) throws IOException {

        imageUploadService.uploadImage(file);
        String imageUrl = imageUploadService.getImageUrl();

        return ResponseEntity.ok(new ImageUrlResponse(imageUrl));
    }
}
