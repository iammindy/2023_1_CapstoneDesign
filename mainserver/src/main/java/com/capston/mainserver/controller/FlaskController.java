package com.capston.mainserver.controller;

import com.capston.mainserver.DTO.FlaskDTO;
import com.capston.mainserver.service.DocumentService;
import com.capston.mainserver.service.FlaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.util.Map;

@RestController
public class FlaskController {

    @Autowired
    private FlaskService flaskService;

    @PostMapping("/process_image")
    public Mono<String> processImage(@RequestBody FlaskDTO.clientDTO clientDTO) throws IOException {
        return flaskService.processImage(clientDTO);
    }


}
