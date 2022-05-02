package com.jobrian.bankappbackend.model;

import java.util.List;

public class CardInfo {
    private String cardHolder;
    private List<Card> cards;

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
