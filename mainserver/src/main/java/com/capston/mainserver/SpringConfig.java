package com.capston.mainserver;

import com.capston.mainserver.domain.Document;
import com.capston.mainserver.repository.DocumentRepository;
import com.capston.mainserver.repository.JpaMemberRepository;
import com.capston.mainserver.repository.MemberRepository;
import com.capston.mainserver.repository.TranslationRepository;
import com.capston.mainserver.service.DocumentService;
import com.capston.mainserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.FluentQuery;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class SpringConfig {

    private final EntityManager em;
    private final DocumentRepository documentRepository;

    @Bean
    public MemberService memberService() { return new MemberService(memberRepository()); }

    @Bean
    public MemberRepository memberRepository() { return new JpaMemberRepository(em); }

}
