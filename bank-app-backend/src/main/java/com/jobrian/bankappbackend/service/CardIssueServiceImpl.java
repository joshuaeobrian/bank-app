package com.jobrian.bankappbackend.service;

import com.jobrian.bankappbackend.model.*;
import com.jobrian.bankappbackend.resource.ExternalBankResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CardIssueServiceImpl implements CardIssueService {
    private final ExternalBankResource externalBankResource;
    private final CardControlService cardControlService;

    @Autowired
    public CardIssueServiceImpl(ExternalBankResource externalBankResource, CardControlService cardControlService) {
        this.externalBankResource = externalBankResource;
        this.cardControlService = cardControlService;
    }

    @Override
    public Card reportCardIssue(CardIssueRequest cardIssue) throws Exception {
        if(cardIssue.getCard() == null || cardIssue.getCard().getCardId() == null){
            throw new Exception("Missing card.");
        }

        String endpoint = "2107a7ca-f0f9-4894-93f3-a6f18e9c9f63/cardcontrols/reportcardissue";
        CardIssue issue = new CardIssue();
        issue.setCardId(cardIssue.getCard().getCardId());
        issue.setCardStatus(cardIssue.getCardStatus());
        issue.setComment(cardIssue.getComment());
        ResponseEntity<GeneralMessage> response = externalBankResource.post(endpoint, issue, GeneralMessage.class);

        if (response == null || response.getBody() == null || !"okay".equals(response.getBody().getMessage())){
            throw new Exception("Issue reporting card issue. Please try again later.");
        }

        CardStatus status = cardIssue.getCardStatus();
        if (CardStatus.LOST.equals(status) || CardStatus.STOLEN.equals(status)){
            return cardControlService.freezeOrUnlockCard(cardIssue.getCard());
        }

        return cardIssue.getCard();
    }
}
