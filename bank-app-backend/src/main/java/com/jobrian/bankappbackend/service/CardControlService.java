package com.jobrian.bankappbackend.service;

import com.jobrian.bankappbackend.model.Card;

public interface CardControlService {
    Card freezeOrUnlockCard(Card card) throws Exception;
}
