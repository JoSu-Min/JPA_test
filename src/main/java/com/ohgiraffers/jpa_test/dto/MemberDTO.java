package com.ohgiraffers.jpa_test.dto;

import com.ohgiraffers.jpa_test.domain.MemberEntity;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private Long number;
    private String userId;
    private String userName;
    private String password;
    private int age;
    public MemberDTO(MemberEntity entity){
        this.number = entity.getNumber();
        this.userId = entity.getUserId();
        this.userName = entity.getUserName();
        this.password = entity.getPassword();
        this.age = entity.getAge();
    }

}
