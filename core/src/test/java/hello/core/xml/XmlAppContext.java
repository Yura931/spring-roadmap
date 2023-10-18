package hello.core.xml;

import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class XmlAppContext {
    GenericXmlApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");

    @Test
    @DisplayName("XML 설정 파일에 등록 된 빈 읽기")
    void xmlAppContext() {

        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
    }

    @Test
    @DisplayName("XML 빈 설정 메타정보 확인")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
            System.out.println("beanDefinitionName = " + beanDefinitionName + "beanDefinition = " + beanDefinition);
        }

        /*
            memberServicebeanDefinition = Generic bean: class [hello.core.member.MemberServiceImpl];
            xml설정파일의 경우 bean이 명확하게 등록되어 있음

            java config의 경우
            팩토리 빈,  팩토리 메서드를 통해서 등록
            직접 임의의 코드를 조작해서 넣을 수 있음
            외부에서 메서드를 호출해서 생성되는 방식
        */
    }
}
