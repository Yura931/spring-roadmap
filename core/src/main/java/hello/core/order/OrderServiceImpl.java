package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    // 역할과 구현을 충실하게 분리 했지만, 정액할인을 정률할인 해주도록 정책을 바꾸었을 때 직접 OrderServiceImpl코드를 고쳐야 하는 문제가 생김
    // OCP, DIP 같은 객체지향 설계 원칙을 준수한 듯 보이지만 사실은 아니다
    // OrderServiceImpl 추상(인터페이스 DiscountPolicy)에 의존하면서 구체(구현 FixDiscountPolicy, RateDiscountPolicy)클래스에도 의존하고 있기 때문
    // 현재 OrderServiceImpl 기능을 확장해서 변경하면 클라이언트 코드에 영향을 주게 되므로 OCP를 위반한 것
    // FixDiscountPolicy를 RateDiscountPolicy로 변경하는 순간 OrderServiceImpl의 소스 코드도 함꼐 변경해야 함 OCP 위반

    private final DiscountPolicy discountPolicy;  // 인터페이스에만 의존하도록 설계, 구현체가 없는데 어떻게 코드를 실행시킬 수 있을까??, 실행 해보면 NPE가 발생

    // 해결방안!
    // 누군가 DiscountPolicy 의 구현객체를 대신 생성하고 주입
    // 관심사의 분리
    // OrderServiceImpl이 할인정책을 직접 선택하게 하면 안됨!!!!

    private final MemberRepository memberRepository;

//   @Autowired 생성자가 하나! 생략 가능
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
