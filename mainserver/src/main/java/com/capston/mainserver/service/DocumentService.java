package com.capston.mainserver.service;

import com.capston.mainserver.domain.Document;
import com.capston.mainserver.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DocumentService {

    private final String UPLOAD_DIR = "C:/Capstone/uploads/"; // 업로드할 디렉토리 경로

    @Autowired
    private DocumentRepository documentRepository;

    public Document uploadDocument(MultipartFile file, Long memberId) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename()); // 파일 이름 가져오기
        String uniqueFileName = Instant.now().toEpochMilli() + "_" + fileName;
        Path uploadPath = Paths.get(UPLOAD_DIR+"/"+memberId); // 업로드할 경로 설정
        Path filePath = uploadPath.resolve(uniqueFileName); // 파일 경로 설정

        //경로 탐색 및 저장
        if (!Files.exists(uploadPath)) { // 경로가 존재하지 않으면 생성
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("Could not create upload directory!");
            }
        }

        // 파일 저장
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", e);
        }

        // Document 객체 생성 및 DB에 저장
        Document document = new Document();
        String fileUrl = filePath.toString(); // 파일 URL 생성
        document.setDocumentName(uniqueFileName);
        document.setMemberId(memberId);
        document.setPath(fileUrl);
        document = documentRepository.save(document);

        return document;
    }

    public Resource loadDocumentAsResource(long documentId) throws IOException {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new FileNotFoundException("Document not found with id " + documentId));
        Path path = Paths.get(document.getPath());
        Resource resource = new UrlResource(path.toUri());
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new FileNotFoundException("Could not find or read file: " + document.getDocumentName());
        }
    }

    public byte[] getPage(Long documentId, int pageIndex) {

        Optional<Document> document = documentRepository.findByDocumentId(documentId);

        if (document.isPresent()) {
            String filePath = document.get().getPath();
            try {
                // 파일 로드
                File file = new File(filePath);

                // PDF 파일을 처리하는 경우
                PDDocument pdfDocument = PDDocument.load(file);

                // pageIndex에 해당하는 페이지 추출
                PDPage page = pdfDocument.getPage(pageIndex-1);  // pageIndex는 1부터 시작하므로 인덱스 조정 필요

                //새 PDF파일 생성
                PDDocument newDocument = new PDDocument();
                newDocument.addPage(page);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                newDocument.save(outputStream);
                newDocument.close();

                return outputStream.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException("Error while processing the document", e);
            }
        } else {
            throw new IllegalArgumentException("Document not found with ID: " + documentId);
        }
    }

    public List<Document> getAllDocumentsByMemberId(Long memberId) {
        return documentRepository.findByMemberId(memberId);
    }

    public void deleteDocument(Long documentId) {
        Optional<Document> optionalDocument = documentRepository.findByDocumentId(documentId);
        if (optionalDocument.isPresent()) {
            Document document = optionalDocument.get();

            // 파일 경로 가져오기
            String filePath = document.getPath();

            // 업로드된 파일 삭제
            try {
                Files.delete(Paths.get(filePath));
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete the uploaded file: " + filePath, e);
            }

            // 데이터베이스에서 문서 삭제
            documentRepository.delete(document);

        } else {
            throw new RuntimeException("Document not found with id: " + documentId);
        }
    }
}
