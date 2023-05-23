package com.capston.mainserver.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity //JPA
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 알아서 고유값 생성
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "member_name")
    private String memberName;
}
