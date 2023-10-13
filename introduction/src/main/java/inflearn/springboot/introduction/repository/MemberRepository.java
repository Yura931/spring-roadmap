package inflearn.springboot.introduction.repository;

import inflearn.springboot.introduction.domain.Member;

import java.util.List;
import java.util.Optional;


public interface MemberRepository {
    Member save(Member member);
    
    // Optional - java8부터 제공, null을 그대로 반환하기보다 감싸서 반환
    Optional<Member> findById(Long id);

    Optional<Member> findByName(String name);
    
    List<Member> findAll();
}
