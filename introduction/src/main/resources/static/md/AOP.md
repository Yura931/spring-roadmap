# AOP(Aspect Oriented Programming)

### AOP가 필요한 상황
> - 모든 메소드의 호출 시간을 측정
> - 공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern)
> - 회원 가입 시간, 회원 조회 시간 측정
> - 원하는 곳에 공통 관심 사항 적용


### 동작 방식
> - AOP 적용 전 의존관계
>   - controller -> service
> - AOP 적용 후 의존관계
>   - controller -> 프록시Service(가짜 스프링 빈, 스프링이 올라올 때 컨테이너에 가짜 스프링 빈을 앞에 세워둠) -> 실제Service
>```java
>   public class MemberController {
>       private final MemberService memberService;
>        @Autowired
>        public MemberController(MemberService memberService) {
>            this.memberService = memberService;
>            System.out.println("memberService = " + memberService.getClass());
>            /*
>                스프링 컨테이너가 AOP가 적용되면 Proxy 가짜를!
>                memberService = class inflearn.springboot.introduction.service.MemberService$$EnhancerBySpringCGLIB$$3f929c38
>                EnhancerBySpringCGLIB
>                CGLibrary 멤버 서비스를 복제해서 코드를 조작하는 기술
>            */
>        }
>   }
>``` 
