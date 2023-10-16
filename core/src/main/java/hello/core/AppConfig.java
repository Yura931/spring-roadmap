package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    // 역할이 잘 보이도록 리팩토링
    // 역할에 대한 구현이 한 눈에 보이도록
    // AppConfig의 등장으로 애플리케이션이 크게 사용 영역과, 객체를 생성하고 구성(Configuration)하는 영역으로 분리 되었다.
    // 구성 영역은 당연히 변경 됨! 공연의 기획자와 같은 역할 구성이 어떻게 되어있는지 구체적으로 알아야 한다.

    @Bean // 스프링 컨테이너에 등록 됨
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }
    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy(); // 할인 정책을 변경하고 싶으면 이 구성 영역의 코드만 변경해주면 된다.
        return new RateDiscountPolicy();
    }

}
