package com.capston.mainserver.service;

import com.capston.mainserver.DTO.FlaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;

@Service
public class FlaskService {

    @Autowired
    private DocumentService documentService;

    public Mono<String> processImage(FlaskDTO.clientDTO clientDTO) throws IOException {
        Long documentId = clientDTO.getDocumentId();
        Resource pdf = documentService.loadDocumentAsResource(documentId);

        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().wiretap(true)))
                .baseUrl("http://127.0.0.1:5000")
                .build();

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("pdf", pdf);
        formData.add("pageIndex", clientDTO.getPageIndex());

        return webClient.post()
                .uri("/process_image")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(formData))
                .retrieve()
                .bodyToMono(String.class);
    }
}
