package com.capston.mainserver.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "translation_id")
    private Long translationId;

    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "page_index")
    private Integer pageIndex;

    @Column(name = "summary")
    private int[] summary;

    @Column(name = "text")
    private String[] text;

    @Column(name = "picture")
    private String[] picture;

}
