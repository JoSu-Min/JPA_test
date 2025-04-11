package com.ohgiraffers.jpa_test.controller;

import com.ohgiraffers.jpa_test.dto.MemberDTO;
import com.ohgiraffers.jpa_test.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MemberController {
    private final MemberService ms; // 회원가입
    @PostMapping("members")
    public ResponseEntity insert(@RequestBody MemberDTO dto){
        log.info("ctrl dto {} :", dto);
        int result = ms.insert(dto);
        if(result == 0)
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
        return ResponseEntity.status(HttpStatus.CONFLICT).body("회원가입 실패 : 존재하는 아이디");
    }
    @PostMapping("login")   // 로그인
    public ResponseEntity login(@RequestBody MemberDTO dto) {
        MemberDTO loginMember = ms.login(dto.getUserId(), dto.getPassword());
        if(loginMember != null) {
            return ResponseEntity.ok().body("로그인 성공");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패 : 일치하지 않는 정보");
    }
    @GetMapping("members")      // 전체회원 조회
    public ResponseEntity list(){
        List<MemberDTO> list = ms.getList();
        if(list == null || list.isEmpty()) {
            return ResponseEntity.ok().body("회원 정보 없음");
        }
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/members/{number}")         // 특정 회원 조회
    public ResponseEntity getData(@PathVariable("number") long number){
        MemberDTO dto = ms.getData(number);
        if(dto != null)
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 정보 없음");
    }

    @PutMapping("/members/{number}")      // 회원 정보 수정
    public ResponseEntity update(@PathVariable("number") Long number,
                                 @RequestBody MemberDTO dto){
        int result = ms.updateData(number, dto);
        if(result == 1)
            return ResponseEntity.status(HttpStatus.OK).body("회원 정보 수정 성공");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 정보 수정 실패");
    }

    @PutMapping("/api/content/{number}")      // 회원 정보 수정 nativeQuery
    public ResponseEntity updateContent(@PathVariable("number") Long number,
                                        @RequestBody MemberDTO dto) {
        int result = ms.updateContent(number, dto);
        if(result == 1)
            return ResponseEntity.status(HttpStatus.OK).body("[NQ] : 정보 수정 성공");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("[NQ] : 회원 정보 없음");
    }

    @DeleteMapping("/members/{number}")         // 회원 삭제
    public ResponseEntity deleteData(@PathVariable("number") Long number) {
        int result = ms.deleteData(number);
        if (result == 1)
            return ResponseEntity.status(HttpStatus.OK).body("회원 삭제 성공");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 삭제 실패");
    }

    @GetMapping("list")   // 페이징 처리된 회원 목록 조회
    public ResponseEntity getPagedMembers(@RequestParam(defaultValue = "0") int start,
                                 @RequestParam(defaultValue = "3")int page){
        List<MemberDTO> list = ms.getPagedMembers(start, page);
        if(list.isEmpty()) {
            return ResponseEntity.ok().body("회원정보 없음");
        }
        return ResponseEntity.ok().body(list);
    }

}









