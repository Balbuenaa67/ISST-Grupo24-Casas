package com.grupo24.cerraduras_casas.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class SeamService {

    private final WebClient webClient;

    @Value("${seam.api.key}")
    private String apiKey;

    public SeamService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://connect.getseam.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<String> abrirCerradura(String deviceId) {
        return webClient.post()
                .uri("/locks/lock-unlock")
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue("""
                    {
                      "device_id": "%s",
                      "action": "unlock"
                    }
                """.formatted(deviceId))
                .retrieve()
                .bodyToMono(String.class);
    }
}