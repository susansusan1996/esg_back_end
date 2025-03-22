package com.esg.controller;

import com.esg.service.ChatGPTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ChatGptController {

    private final static Logger log = LoggerFactory.getLogger(ChatGptController.class);

    @Autowired
    private ChatGPTService chatGPTService;

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody String prompt) throws IOException {
        log.info("開始使用gpt功能");
        return ResponseEntity.ok().body(chatGPTService.chatGPT(null,prompt));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestPart("file") MultipartFile file, @RequestParam("prompt") String prompt) throws IOException {
        log.info("開始使用gpt分析pdf");
        return ResponseEntity.ok().body(chatGPTService.handleUpload(file,prompt));
    }



}
