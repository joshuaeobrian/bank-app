package com.jobrian.bankappbackend.service;

import com.jobrian.bankappbackend.model.Card;
import com.jobrian.bankappbackend.model.GeneralMessage;
import com.jobrian.bankappbackend.resource.ExternalBankResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CardControlServiceImpl implements CardControlService{
    private final ExternalBankResource externalBankResource;

    @Autowired
    public CardControlServiceImpl(ExternalBankResource externalBankResource) {
        this.externalBankResource = externalBankResource;
    }

    @Override
    public Card freezeOrUnlockCard(Card card) throws Exception {
        if(card == null){
            throw new Exception("Missing Card");
        }
        if (card.getCardId() == null){
            throw new Exception("Card is missing cardId");
        }
        String endpoint = String.format("2107a7ca-f0f9-4894-93f3-a6f18e9c9f63/cardcontrols/onoff/%s", card.getCardId());
        ResponseEntity<GeneralMessage> response = externalBankResource.post(endpoint, GeneralMessage.class);
        GeneralMessage message = response.getBody();
        if(message == null || !"okay".equals(message.getMessage())){
            throw new Exception("Failed to updated card");
        }
        card.setActive(!card.isActive());
        return card;
    }
}
