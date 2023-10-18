package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanDefinitionTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("beanDefinitionName = " + beanDefinitionName + " beanDefinition = " + beanDefinition);
            }
        }
        /*
            beanDefinitionName = appConfig
            beanDefinition = Generic bean: class [hello.core.AppConfig$$EnhancerBySpringCGLIB$$1ac4a1de]; scope=singleton; abstract=false; lazyInit=null; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null
            factoryBeanName=appConfig; factoryMethodName=memberService;
            자바 설정 파일은 팩토리 메서드를 통해 제공 됨
            lazy : 스프링빈은 원래 스프링 컨테이너가 뜰 때 다 등록이 되지만 실제 사용하는 시점에  스프링 빈을 초기화 하도록 할 수 있는 정보들
        */

        // 스프링의 다양한 설정 정보를 빈 데피니션으로 추상화해서 제공한다.
        // BeanDefinition을 직접 생성해서 스프링 컨테이너에 등록 할 수도 있지만 실무에서 직접 정의하거나 사용할 일은 거의 없다.
    }
}
