package com.capston.mainserver.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "documents")
public class Document {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "path")
    private String path;

}
