package com.esg.controller;

import com.esg.service.ChatGPTService;
import com.esg.service.OpenDataService;
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
public class OpenDataController {

    private final static Logger log = LoggerFactory.getLogger(OpenDataController.class);

    @Autowired
    private OpenDataService openDataService;

    @PostMapping("/get-carbon")
    public ResponseEntity<String> getCarbon(){
        log.info("查詢碳排放量");
        return ResponseEntity.ok().body(openDataService.getCarbon());
    }
}
