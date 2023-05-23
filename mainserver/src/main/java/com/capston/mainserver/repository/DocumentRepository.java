package com.capston.mainserver.repository;

import com.capston.mainserver.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByMemberId(Long memberId);

    Optional<Document> findByDocumentId(Long documentId);
}
