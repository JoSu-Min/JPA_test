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
        try{
            MemberEntity entity = repo.save( new MemberEntity(dto));
            log.info("service entity : {}", entity);
        }catch (Exception e) {
            result =1;
            e.printStackTrace();
        }

        return result;
    }

    public MemberDTO login(String userId, String password) {
        MemberEntity entity = repo.findByUserIdAndPassword(userId, password);
        if(entity != null) {
            return new MemberDTO(entity);
        }
        return null;
    }

    public List<MemberDTO> getList() {
        List<MemberDTO> list = null;
        List<MemberEntity> listE = repo.findAll();
        if(listE.size() != 0) {
            list = listE.stream()
                    .map(m-> new MemberDTO(m))
                    .toList();
        }

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

    public int updateData(Long number, MemberDTO dto){
        Optional<MemberEntity> opEntity = repo.findById(number);
        if(opEntity.isPresent()) {
            MemberEntity entity = opEntity.get();
            entity.setUserName(dto.getUserName());
            entity.setAge(dto.getAge());
            entity.setPassword(dto.getPassword());
            repo.save(entity);
            return 1;
        }
        return 0;
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

    public List<MemberDTO> getPagedMembers(int start, int page) {
        Pageable pageable = PageRequest.of(start, page,
                                            Sort.by(Sort.Order.desc("number")));
        Page<MemberEntity> pageEntity = repo.findAll(pageable);
        List<MemberEntity> listE = pageEntity.getContent();
        List<MemberDTO> list = listE.stream()
                                    .map(m-> new MemberDTO(m))
                                    .toList();
        return list;
    }

    public int updateContent(Long number, MemberDTO dto) {
        int result = 1;
        try{
            result = repo.updateContent(number,
                                        dto.getUserName(),
                                        dto.getAge(),
                                        dto.getPassword());
            log.info("update : {} ", result);
        }catch (Exception e){
            e.printStackTrace();;
        }
        return result;
    }
}
