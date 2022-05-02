package com.jobrian.bankappbackend.controller;

import com.jobrian.bankappbackend.model.CardInfo;
import com.jobrian.bankappbackend.model.GeneralMessage;
import com.jobrian.bankappbackend.service.CardInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/card-info")
public class CardInfoController {
    private final CardInfoService cardInfoService;

    @Autowired
    public CardInfoController(CardInfoService cardInfoService) {
        this.cardInfoService = cardInfoService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCardInfo(@PathVariable String userId){
        try {
            return ResponseEntity.ok(cardInfoService.getCardInfo(userId));
        } catch (Exception e) {
            GeneralMessage message = new GeneralMessage();
            message.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(message);
        }
    }
}
