package com.ohgiraffers.jpa_test.rep;

import com.ohgiraffers.jpa_test.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepo extends JpaRepository<MemberEntity, Long> {
    MemberEntity findByUserId(String userId);

    @Query(value = "select * from member_test where number=:n",
                                                    nativeQuery = true)
    MemberEntity findByContent(@Param("n")long num);

    @Modifying
    @Transactional
    @Query(value="insert into member_test(user_id, user_name, age)"+
                  "values (:id, :name, :age)", nativeQuery = true)
    int insertContent (@Param("id")String userId,
                          @Param("name") String userName,
                          @Param("age") int age);

}

//MemberRepo repo; repo.함수 -> 이전 사용방식
