package inflearn.springboot.introduction.repository;

import inflearn.springboot.introduction.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    // 저장 조회, 업데이트는 sql 짤 필요 없음
    // pk 기반이 아닌 나머지들은 jpql을 작성해 주어야 함
    // JPA 기술을 스프링에 감싸서 담는 기술 - data-jpa -> 사용하면 pk 기반이 아닌 쿼리도 jpql 만들지 않아도 됨
    // jpa는 항상 트랜잭션이 있어야 함, join들어 올 때 모든 데이터 변경이 transaction 안에서 되어야 함

    private final EntityManager em; // jpa 라이브러리, 스프링 부트가 EntityManager를 자동으로 생성, 만들어진 것을 Injection해서 사용

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        /*
            Hibernate: select member0_.id as id1_0_, member0_.name as name2_0_ from member member0_ where member0_.name=?
            Hibernate: insert into member (id, name) values (default, ?)
            hibernate 오픈소스 구현체 사용

        * */
        em.persist(member); // 영구 저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name=:name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class) // jpql 쿼리, 테이블 대상으로 쿼리를 날리는데 jpql은 객체(Entity)를 대상으로 쿼리를 날림
                .getResultList();
    }
}
