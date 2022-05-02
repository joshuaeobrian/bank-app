package com.jobrian.bankappbackend.controller;

import com.jobrian.bankappbackend.model.*;
import com.jobrian.bankappbackend.service.CardIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/card-issue")
public class CardIssueController {
    private final CardIssueService cardIssueService;

    @Autowired
    public CardIssueController(CardIssueService cardIssueService) {
        this.cardIssueService = cardIssueService;
    }

    @GetMapping("/statuses")
    public ResponseEntity<?> getCardStatuses(){
        return ResponseEntity.ok(CardStatus.values());
    }
    @PostMapping("/report")
    public ResponseEntity<?> reportCardIssue(@RequestBody CardIssueRequest cardIssue){
        try {
            return ResponseEntity.ok(cardIssueService.reportCardIssue(cardIssue));
        } catch (Exception e) {
            GeneralMessage message = new GeneralMessage();
            message.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(message);
        }
    }
}
