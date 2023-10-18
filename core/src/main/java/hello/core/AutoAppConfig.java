package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
//        basePackages = "hello.core",
//        basePackageClasses = AutoAppConfig.class, // AutoAppConfig.class의 경로인 hello.core가 시작위치가 됨
// 따로 설정하지 않는 경우 @ComponentScan이 붙은 설정 정보 클래스의 패키지가 시작 위치가 됨
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class) // AppConfig클래스, 테스트 코드에 만든 @Configuration이 붙은 클래스 Bean으로 등록되지 않도록 제외, 예제코드를 위한 필터
)
// @Component애노테이션이 붙은 클래스를 스프링 빈으로 자동 등록
// 자동으로 스프링 빈을 등록해주기 때문에 @Autowired를 사용해 의존관계를 자동으로 주입해주면 된다.
public class AutoAppConfig {
    @Bean(name = "memoryMemberRepository")
    MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
