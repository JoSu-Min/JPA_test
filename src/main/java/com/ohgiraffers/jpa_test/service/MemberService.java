package com.ohgiraffers.jpa_test.service;

import com.ohgiraffers.jpa_test.domain.MemberEntity;
import com.ohgiraffers.jpa_test.dto.MemberDTO;
import com.ohgiraffers.jpa_test.rep.MemberRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {
    private final MemberRepo repo;

    public int insert(MemberDTO dto) {
        int result = 0;
        //insert, update
        try{
            MemberEntity entity = repo.save( new MemberEntity(dto)); // 인터페이스에서 설정한 entity값으로 설정
            log.info("service entity : {}", entity);
        }catch (Exception e) {
            result =1;
//            throw new RuntimeException();
            e.printStackTrace();
        }

        return result;
    }

    public List<MemberDTO> getList() {
        List<MemberDTO> list = null;
        List<MemberEntity> listE = repo.findAll();
        if(listE.size() != 0) {
            list = listE.stream()
                    .map(m-> new MemberDTO(m))
                    .toList();
        }
//        List<MemberEntity> listE = repo.findAll(); // findAll = select * 과 동일
//
//        for(MemberEntity e : listE){
//            list.add(new MemberDTO(e));
//        }

        log.info("list entity : {}", listE);
        return  list;
    }

    public MemberDTO getData(long number) {
        Optional<MemberEntity> opM = repo.findById(number); // PK로 값을 가져옴
        MemberEntity entity = opM.orElse(null);
        if(entity != null)
            return new MemberDTO(entity);
        return  null;

    }

    public int deleteData(Long num) {
        int result = 0;
        MemberDTO dto = getData(num);
        if(dto != null){
            repo.deleteById(num);
            result = 1;
        }
        return result;
    }
    public int updateData(String userId, MemberDTO dto){
        MemberEntity entity = repo.findByUserId(userId);

        log.info("service update : {}", entity);
        if( entity != null ) {
            entity.setUserName(dto.getUserName());
            entity.setAge(dto.getAge());
            repo.save(entity);
            return 1;
        }
        return 0;
    }

    public List<MemberDTO> getListPage(int start, int page) {
//        int page = 3;
        Pageable pageable = PageRequest.of(start, page,
                                            Sort.by(Sort.Order.desc("number")));
        Page<MemberEntity> pageEntity = repo.findAll(pageable);
        List<MemberEntity> listE = pageEntity.getContent();
        List<MemberDTO> list = listE.stream()
                                    .map(m-> new MemberDTO(m))
                                    .toList();
        return list;

    }

    public MemberDTO getContents(long number) {
        MemberEntity entity = repo.findByContent(number); // PK로 값을 가져옴
//        log.info("entity : {} ", entity);
        if(entity != null)
            return new MemberDTO(entity);
        return null;
    }

    public int insertContent(MemberDTO dto) {
        int result = 0;
        //insert, update
        try{
            result = repo.insertContent( dto.getUserId(),
                                         dto.getUserName(),
                                         dto.getAge()); // 인터페이스에서 설정한 entity값으로 설정
            log.info("service result : {}", result);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
