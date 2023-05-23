package com.capston.mainserver.service;

import com.capston.mainserver.domain.Translation;
import com.capston.mainserver.repository.TranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TranslationService {

    @Autowired
    private TranslationRepository translationRepository;
    
    public Translation createTranslation(Translation translation) {
        return translationRepository.save(translation);
    }

    public Translation getTranslationById(Long translationId) {
        Optional<Translation> translationOptional = translationRepository.findById(translationId);
        return translationOptional.orElse(null);
    }
    
    public void deleteTranslation(Long translationId) {
        translationRepository.deleteById(translationId);
    }
}