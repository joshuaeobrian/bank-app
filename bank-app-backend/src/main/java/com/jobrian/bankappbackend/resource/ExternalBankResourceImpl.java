package com.jobrian.bankappbackend.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class ExternalBankResourceImpl implements ExternalBankResource {
    @Value("${external.apiKey}")
    private String apiKey;
    @Value("${external.urlBase}")
    private String urlBase;

    public <T> ResponseEntity<T> get(String path, Class<T> responseType){
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> request = RequestEntity.get(URI.create(getFullPath(path)))
                .accept(MediaType.APPLICATION_JSON)
                .header("API-Key", apiKey)
                .build();

        return restTemplate.exchange(request, responseType);
    }

    public <T> ResponseEntity<T> post(String path, Class<T> responseType){
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> request = RequestEntity.post(URI.create(getFullPath(path)))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("API-Key", apiKey)
                .build();

        return restTemplate.exchange(request, responseType);
    }

    @Override
    public <T> ResponseEntity<T> post(String path, Object body, Class<T> responseType) {
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Object> request = RequestEntity.post(URI.create(getFullPath(path)))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("API-Key", apiKey)
                .body(body);

        return restTemplate.exchange(request, responseType);
    }

    private String getFullPath(String path){
        return String.format("%s/%s", urlBase, path);
    }
}
