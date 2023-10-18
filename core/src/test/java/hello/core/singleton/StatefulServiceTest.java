package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

public class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA: A사용자 10000원 주문
        // 공유 필드를 사용하지 않고 지역변수로 받아서 분리
        int price1 = statefulService1.order("userA", 10000);


        // ThreadB: B사용자 20000원 주문
        statefulService2.order("userB", 20000);

        // ThreadA: 사용자A 주문 금액 조회
//        int price1 = statefulService1.getPrice();
          System.out.println("price1 = " + price1);

        // 사용자A는 만원을 주문 했는데.. B사용자가 끼어들어 이만원으로 변경이 되어버렸네..
        // StatefulService는 싱글톤으로 관리되는 객체이기 때문에 필드를 공유하게 됨
//        assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }
    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}
