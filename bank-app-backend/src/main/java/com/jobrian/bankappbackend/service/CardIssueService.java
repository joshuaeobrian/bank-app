package com.jobrian.bankappbackend.service;

import com.jobrian.bankappbackend.model.Card;
import com.jobrian.bankappbackend.model.CardIssueRequest;

public interface CardIssueService {
    Card reportCardIssue(CardIssueRequest cardIssue) throws Exception;
}
