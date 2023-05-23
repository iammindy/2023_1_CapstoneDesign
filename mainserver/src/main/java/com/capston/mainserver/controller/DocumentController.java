package com.capston.mainserver.controller;

import com.capston.mainserver.domain.Document;
import com.capston.mainserver.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/document")
    public ResponseEntity<Document> uploadDocument(@RequestParam("file") MultipartFile file, @RequestParam("memberId") Long memberId) {
        Document document = documentService.uploadDocument(file, memberId);
        return ResponseEntity.ok().body(document);
    }

    @GetMapping("/document/{documentId}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable long documentId) {
        try {
            Resource resource = documentService.loadDocumentAsResource(documentId);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to download file", e);
        }
    }

    @GetMapping("/document/{documentId}/{pageIndex}")
    public ResponseEntity<byte[]> getPage(@PathVariable("documentId") Long documentId, @PathVariable("pageIndex") int pageIndex) {
        byte[] pageBytes = documentService.getPage(documentId, pageIndex);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(pageBytes.length);

        return new ResponseEntity<>(pageBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/document/all/{memberId}")
    public List<Document> getAllDocumentsByMemberId(@PathVariable Long memberId) {
        return documentService.getAllDocumentsByMemberId(memberId);
    }

    @DeleteMapping("/document/{documentId}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long documentId) {
        documentService.deleteDocument(documentId);
        return ResponseEntity.ok("Document deleted successfully");
    }








}
