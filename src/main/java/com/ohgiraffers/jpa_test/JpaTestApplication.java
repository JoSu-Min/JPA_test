package com.ohgiraffers.jpa_test;

import com.ohgiraffers.jpa_test.domain.MemberEntity;
import com.ohgiraffers.jpa_test.dto.MemberDTO;
import com.ohgiraffers.jpa_test.rep.MemberRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpaTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaTestApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(MemberRepo repo) {
        return args -> {
            MemberDTO dto = new MemberDTO(null, "testuser", "홍길동", "1234", 30);
            repo.save(new MemberEntity(dto));
        };
    }
}
