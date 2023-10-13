package inflearn.springboot.introduction.service;

import inflearn.springboot.introduction.domain.Member;
import inflearn.springboot.introduction.repository.MemberRepository;
import inflearn.springboot.introduction.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
public class MemberService {

    // @Autowired 필드 주입, 별로 권장하지 않음, 스프링이 뜰 때만 주입이 가능하고 중간에 작업할 수가 없음
    private final MemberRepository memberRepository;

    // memberService에서 직접 new MemberRepository를 사용해 인스턴스를 만들지 않고
    // 외부에서 주입해주는 방식으로 사용
    // 이런 방식을 Dependency Injection이라 함
    @Autowired
    public MemberService(MemberRepository memberRepository) {   // DI 생성자 주입, 제일 권장하는 방법!
        this.memberRepository = memberRepository;
    }


    // setter주입, set메소드가 public으로 열려있기 때문에 memberService.setMemberRepository(); 이런식으로 아무 개발자나 다 호출이 가능해져버림
    // 개발은 최대한 호출되지 않아야 하는 메서드를 호출하면 안 됨
    // 조립 시점에 생성자로 조립 해두고 끝!!
    // setMemberRepository 중간에 잘못 바꾸면 문제가 생김, 한 번 세팅이 된 후 변경되지 않도록!
/*
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
*/

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // 룰 : 같은 이름이 있는 중복 회원X

        // 중복 회원 검증
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // Optional을 바로 반환하는게 이쁘지 않음
//        Optional<Member> result = memberRepository.findByName(member.getName());
        memberRepository.findByName(member.getName())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    /**
     * 전체 멤버 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
