package com.capston.mainserver.service;

import com.capston.mainserver.domain.Document;
import com.capston.mainserver.repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DocumentServiceTest {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentRepository documentRepository;

    @LocalServerPort
    private int port;

    @Test
    public void 문서업로드() throws Exception {

        //given
        File file = new File(getClass().getResource("/test.pdf").getFile());
        MockMultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "application/pdf", Files.readAllBytes(file.toPath()));

        //when
        Document savedDocument = documentService.uploadDocument(multipartFile, 1L);

        //then
        assertNotNull(savedDocument.getDocumentId());
        assertEquals(file.getName(), savedDocument.getDocumentName());
        assertEquals("/uploads/" + file.getName(), savedDocument.getPath());

        Optional<Document> foundDocument = documentRepository.findById(savedDocument.getDocumentId());
        assertTrue(foundDocument.isPresent());
        assertEquals(savedDocument.getDocumentId(), foundDocument.get().getDocumentId());
        assertEquals(savedDocument.getDocumentName(), foundDocument.get().getDocumentName());
        assertEquals(savedDocument.getPath(), foundDocument.get().getPath());
    }

}