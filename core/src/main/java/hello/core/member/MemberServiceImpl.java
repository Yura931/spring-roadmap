package hello.core.member;

public class MemberServiceImpl implements MemberService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository(); // 추상화에도 의존하고 구현체에도 의존, 변경 시 문제가 생김
    private final MemberRepository memberRepository;

    // MemberServiceImpl이 직접 MemoryMemberRepository를 선택하지 않도록, 생성자를 통해서 MemberRepository 구현체 선택
    // MemberServiceImpl에는 MemberRepository라는 추상화 인터페이스만 남게 됨 / DIP를 만족하게 된다.
    // 의존관계에 대한 고밍는 외부에 맡기고 실행에만 집중!
    // 의존관계를 마치 외부에서 주입해주는 것 같다고 해서 DI(Dependency Injection) 의존관계 주입 또는 의존성 주입이라고 한다.
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
