package inflearn.springboot.introduction.repository;

import inflearn.springboot.introduction.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 스프링 데이터 JPA가 JPA 리포지토리를 받고 있으면 구현체를 자동으로 만들어주고 스프링 빈에 자동으로 등록해줌, 내가 등록하는게 아니라 스프링 데이터 JPA가 보고 구현체를 만들어 등록!
// 가져다 사용하기만 하면 됨!
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {    // Entity클래스와 PK의 타입, 인터페이스는 다중 상속 가능
    @Override
    Optional<Member> findByName(String name);
}
