package com.jobrian.bankappbackend.service;

import com.jobrian.bankappbackend.model.CardInfo;

public interface CardInfoService {
    CardInfo getCardInfo(String userId) throws Exception;
}
