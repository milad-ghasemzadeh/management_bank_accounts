package org.management.controller;

import org.management.dto.SenderDTO;
import org.management.service.SenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SenderController {

    private final SenderService senderService;
    private final Logger logger = LoggerFactory.getLogger(getClass());


    public SenderController(SenderService senderService) {
        this.senderService = senderService;
    }

    // getting all of senders
    @GetMapping("/sender")
    public ResponseEntity<List<SenderDTO>> getAllOfSenders() {
        logger.info("get All of senders");
        return new ResponseEntity<>(senderService.getAllOfSenders(), HttpStatus.OK);
    }

}
