package inflearn.springboot.introduction.service;

import inflearn.springboot.introduction.domain.Member;
import inflearn.springboot.introduction.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    // 테스트는 과감하게 한글로 바꿔도 됩니다!!
    // 빌드 될 때 실제 코드에 포함되지 않음
    // 테스트는 정상플로우도 중요하지만 예외플로우가 정말 중요하다.

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        // iv의 memberRepository에 new연산자로 직접 객체를 만들게 되면
        // MemberService의 memberRepository와 MemberServiceTest의 memberRepository가 각각 다른 주소를 갖는 객체에서 데이터를 가져오게 됨
        // iv에 직접 객체를 생성하기 보다는 생성자를 통해 외부에서 주입해주는 방식을 권장
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {

        // given
        Member member = new Member();
        member.setName("spring");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복회원예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));// memberService.join(member2) 로직이 돌면서 IllegalStateException 예외가 발생해야 함, 반환이 됨, 반환 값으로 메시지 확인도 가능
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
/*
        try {
            memberService.join(member2);
            fail("예외가 발생해야 합니다.");
        } catch(IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.123123");
        }
*/


        // then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}