package com.capston.mainserver.controller;

import com.capston.mainserver.domain.Translation;
import com.capston.mainserver.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TranslationController {

    @Autowired
    private TranslationService translationService;

    @PostMapping("/translation")
    public ResponseEntity<Translation> createTranslation(@RequestBody Translation translation) {
        Translation createdTranslation = translationService.createTranslation(translation);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTranslation);
    }

    @GetMapping("/translation/{translationId}")
    public ResponseEntity<Translation> getTranslation(@PathVariable("translationId") Long translationId) {
        Translation translation = translationService.getTranslationById(translationId);
        if (translation != null) {
            return ResponseEntity.ok(translation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/translation/{translationId}")
    public ResponseEntity<Void> deleteTranslation(@PathVariable("translationId") Long translationId) {
        translationService.deleteTranslation(translationId);
        return ResponseEntity.noContent().build();
    }

}
