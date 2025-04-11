package com.ohgiraffers.jpa_test.rep;

import com.ohgiraffers.jpa_test.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepo extends JpaRepository<MemberEntity, Long> {
    MemberEntity findByUserIdAndPassword(String userId, String password);

    @Modifying
    @Transactional
    @Query(value = "UPDATE member_test " +
                   "SET user_name = :name, " +
                   "    age = :age, " +
                   "    password = :password " +
                   "WHERE number = :num", nativeQuery = true)
    int updateContent(@Param("num") Long number,
                     @Param("name") String userName,
                     @Param("age") int age,
                     @Param("password") String password);
}

//MemberRepo repo; repo.함수 -> 이전 사용방식
