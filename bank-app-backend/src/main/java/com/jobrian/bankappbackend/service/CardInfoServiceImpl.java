package com.jobrian.bankappbackend.service;

import com.jobrian.bankappbackend.model.CardInfo;
import com.jobrian.bankappbackend.resource.ExternalBankResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class CardInfoServiceImpl implements CardInfoService{

    private final ExternalBankResource externalBankResource;

    @Autowired
    public CardInfoServiceImpl(ExternalBankResource externalBankResource) {
        this.externalBankResource = externalBankResource;
    }

    @Override
    public CardInfo getCardInfo(String userId) throws Exception {
        if (userId == null){
            throw new Exception("Missing userId");
        }
        String endpoint = String.format("2107a7ca-f0f9-4894-93f3-a6f18e9c9f63/cardInfo/%s", userId);
        ResponseEntity<CardInfo> response = externalBankResource.get(endpoint, CardInfo.class);
        CardInfo cardInfo = response.getBody();
        if(cardInfo == null){
            throw new Exception("Could not lookup info on user");
        }else if(cardInfo.getCards() != null){
            //Setting initial value to allow backend to manage it
            cardInfo.getCards().forEach(card -> card.setActive(true));
        }
        return cardInfo;
    }
}
