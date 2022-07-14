package com.study.datajpa.repository;

import com.study.datajpa.dto.MemberDto;
import com.study.datajpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member>findByUsernameAndAgeGreaterThan(String username, int age);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member>findUser(@Param("username")String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String>findUsernameList();

    @Query("select new com.study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto>findMemberDto();

    List<Member>findListByUsername(String username);            //컬렉션
    Member findMemberListByUsername(String username);           //단건
    Optional<Member>findOptionalByUsername(String username);    //단건 Optional

    Page<Member>findByAge(int age, Pageable pageable);
    //Slice<Member> findByAge(int age, Pageable pageable);

//    @Query(value = "select m from Member m left join m.team t",
//                countQuery = "select count(m) from Member m")
//    Page<Member>findByAge(int age, Pageable pageable);
}
