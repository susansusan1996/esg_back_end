package com.esg.controller;

import com.esg.service.ChatGPTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatGptController {

    private final static Logger log = LoggerFactory.getLogger(ChatGptController.class);

    @Autowired
    private ChatGPTService chatGPTService;

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody String prompt){
        log.info("開始使用gpt功能");
        return ResponseEntity.ok().body(chatGPTService.chatGPT(prompt));
    }


}
