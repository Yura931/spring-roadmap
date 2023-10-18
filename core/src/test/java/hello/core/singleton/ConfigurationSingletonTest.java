package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        // !같은 객체!
        System.out.println("memberRepository1 = " + memberRepository1);
        System.out.println("memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);
        /*
            memberRepository1 = hello.core.member.MemoryMemberRepository@4218500f
            memberRepository2 = hello.core.member.MemoryMemberRepository@4218500f
            memberRepository = hello.core.member.MemoryMemberRepository@4218500f

            AppConfig 내에서 분명 new 연산자로 객체를 만든 것 같은데..?
            같은 인스턴스가 조회 된다.

            call AppConfig.memberService
            call AppConfig.memberRepository -> memberRepository 호출이 한번만 된다.
            call AppConfig.orderService

            이게 모지?
            스프링이 정말 어떠한 방법을 써서라도 싱글톤을 보장해 주는구나!

        */
        assertThat(memberRepository1).isSameAs(memberRepository);
        assertThat(memberRepository2).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);    // AppConfig고 스프링 빈으로 등록 됨
        AppConfig bean = ac.getBean(AppConfig.class);
        System.out.println("bean = " + bean.getClass());
        /*
            bean = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$5fc2e21d
            CGLIB -> 내가 만든 클래스가 아니라 스프링이 CGLIB라는 바이트코드 조작 라이브러리를 사용해서 AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들고,
            다른 클래스를 스프링 빈으로 등록한 것

            이 임의의 다른 클래스가 싱글톤이 보장되도록 해준다.

            @Configuration 제거 시
            bean = class hello.core.AppConfig -> 내가 등록한 클래스가 그대로 등록

            call AppConfig.memberService
            call AppConfig.memberRepository
            call AppConfig.memberRepository
            call AppConfig.orderService
            call AppConfig.memberRepository

            memberRepository를 세번 호출
            싱글톤을 보장하지 않음
        */
    }
}
