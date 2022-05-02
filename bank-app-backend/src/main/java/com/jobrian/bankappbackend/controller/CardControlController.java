package com.jobrian.bankappbackend.controller;

import com.jobrian.bankappbackend.model.Card;
import com.jobrian.bankappbackend.model.GeneralMessage;
import com.jobrian.bankappbackend.service.CardControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/card-control")
public class CardControlController {
    private final CardControlService cardControlService;

    @Autowired
    public CardControlController(CardControlService cardControlService) {
        this.cardControlService = cardControlService;
    }

    @PutMapping("/update-status")
    public ResponseEntity<?> freezeOrUnfreezeCard(@RequestBody Card card) {
        try {
            return ResponseEntity.ok(cardControlService.freezeOrUnlockCard(card));
        } catch (Exception e) {
            GeneralMessage message = new GeneralMessage();
            message.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(message);
        }
    }
}
