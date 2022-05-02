package com.jobrian.bankappbackend.service;

import com.jobrian.bankappbackend.model.Card;
import com.jobrian.bankappbackend.model.GeneralMessage;

public interface CardControlService {
    Card freezeOrUnlockCard(Card card) throws Exception;
}
