package inflearn.springboot.introduction;

import inflearn.springboot.introduction.aop.TimeTraceAop;
import inflearn.springboot.introduction.repository.*;
import inflearn.springboot.introduction.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final MemberRepository memberRepository;    // spring data가 구현체를 만들어 등록해준 빈을 가져다 사용하기만 하면 됨!

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
    private DataSource dataSource;

    // spring boot가 설정파일을 보고 자체적으로 bean을 생성해 줌
    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
*/

//    @PersistenceContext
/*    private EntityManager em;

    public SpringConfig(EntityManager em) {
        this.em = em;
    }*/


    // 직접 스프링 빈 등록
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

//    @Bean
//    public MemberRepository memberRepository() {
//        return new JdbcMemberRepository(dataSource);
//        return new MemoryMemberRepository();
//        return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);
//
//    }

    // AOP가 등록되어 쓰이는 군!
//    @Bean
//    public TimeTraceAop timeTraceAop() {
//        return new TimeTraceAop();
//    }
}
