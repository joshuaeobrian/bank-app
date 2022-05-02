package com.jobrian.bankappbackend.resource;

import org.springframework.http.ResponseEntity;

public interface ExternalBankResource {
    <T> ResponseEntity<T> get(String path, Class<T> responseType);
    <T> ResponseEntity<T> post(String path, Class<T> responseType);
    <T> ResponseEntity<T> post(String path, Object body,  Class<T> responseType);
}
