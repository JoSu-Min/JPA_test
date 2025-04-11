package com.ohgiraffers.jpa_test.domain;

import com.ohgiraffers.jpa_test.dto.MemberDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// DB와 소통은 무조건 Entity 사용
@Entity
@Table(name = "member_test") // 테이블 이름 지정 어노테이션
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    @NotNull
    @Column(unique = true,length = 20)
    private  String userId;// 컬럼명은 user_id로 나옴

    @Column(name = "user_name", nullable = false, length = 30)
    private String userName;

    @Column(name = "age")
    private int age;

    public MemberEntity(MemberDTO dto){

        this.userId = dto.getUserId();
        this.userName = dto.getUserName();
        this.age = dto.getAge();

    }

}
