package inflearn.springboot.introduction.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component // 현재는 Bean으로 등록 시 Bean 등록시점 때문에 참조순환 에러가 남
public class TimeTraceAop { // ComponentScan보다는 Bean등록하는 방식을 권장
    @Around("execution(* inflearn.springboot.introduction..*(..))") // 어디에 적용할 것인가! 타겟팅 가능
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed(); // 다음 메소드 진행
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }

    }
}
