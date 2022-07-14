package com.study.datajpa.repository;

import com.study.datajpa.dto.MemberDto;
import com.study.datajpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member>findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member>findByUsername(String username);
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

    @Modifying
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age")int age);

    //Member가 조회될 때 한번에 team도 가져온다. 원래는 Lazy로 되어있으면 Proxy객체를 가져온다.
    @Query("select m from Member m left join fetch m.team")
    List<Member>findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member>findAll();

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member>findLockByUsername(String username);
}
