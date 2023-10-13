package inflearn.springboot.introduction.repository;

import inflearn.springboot.introduction.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    // test는 순서가 보장되지 않음
    // 순서에 의존적으로 설정하면 절대 안됨!(순서를 정해주는 어노테이션이 있긴 한데 ...)
    // findAll()이 먼저 실행되고 findByName()이 실행되면 spring1이라는 키값을 갖는 Member객체가 저장이되어 findByName()의 Member객체와 repository가 저장하고 있는 Member객체가 다른 주소값을 가지게 되는 경우가 생김
    // 테스트가 끝나고 나면 데이터를 클리어 해주어야 함
    MemoryMemberRepository repository = new MemoryMemberRepository();
    
    // 하나의 Test가 끝날 때 마다 실행 됨
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);
        Member result = repository.findById(member.getId()).get();
        // System.out.println("(result == member) = " + (result == member));
//        Assertions.assertEquals(member, null);
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}