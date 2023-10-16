# 좋은 객체 지향 설계의 5가지 원칙의 적용

### SRP 단일 책임 원칙
**한 클래스는 하나의 책임만 가져야 한다.**

- 기존의클라이언트 객체는 직접 구현 객체를 생성하고, 연결하고, 실행하는 다양한 책임을 가지고 있음
```java
//기존 코드
public class MemberServiceImpl implements MemberService {

private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
```
- SRP 단일 책임 원칙을 따르면서 관심사를 분리함
- 클라이언트 객체는 실행하는 책임만 담당
```java
//변경 코드
public class MemberServiceImpl implements MemberService {
    
    private final MemberRepository memberRepository;
    
    // MemberServiceImpl이 직접 MemoryMemberRepository를 선택하지 않도록, 생성자를 통해서 MemberRepository 구현체 선택
    // MemberServiceImpl에는 MemberRepository라는 추상화 인터페이스만 남게 됨 / DIP를 만족하게 된다.
    // 의존관계에 대한 고밍는 외부에 맡기고 실행에만 집중!
    // 의존관계를 마치 외부에서 주입해주는 것 같다고 해서 DI(Dependency Injection) 의존관계 주입 또는 의존성 주입이라고 한다.
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
```
- 구현 객체를 생성하고 연결하는 책임은 AppConfig가 담당
```java
public class AppConfig {
    // 역할이 잘 보이도록 리팩토링
    // 역할에 대한 구현이 한 눈에 보이도록
    // AppConfig의 등장으로 애플리케이션이 크게 사용 영역과, 객체를 생성하고 구성(Configuration)하는 영역으로 분리 되었다.
    // 구성 영역은 당연히 변경 됨! 공연의 기획자와 같은 역할 구성이 어떻게 되어있는지 구체적으로 알아야 한다.
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
```

### DIP 의존관계 역전 원칙
**프로그래머는 "추상화에 의존해야지, 구체화에 의존하면 안된다." 의존성 주입은 이 원칙을 따르는 방법 중 하나**
- `OrderServiceImpl`는 DIP를 지키며 `DiscountPolicy`추상화 인터페이스에 의존하는 것 같았지만, `FixDiscountPolicy` 구체화 구현 클래스도 함께 의존
```java
// 기존 코드
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    // 역할과 구현을 충실하게 분리 했지만, 정액할인을 정률할인 해주도록 정책을 바꾸었을 때 직접 OrderServiceImpl코드를 고쳐야 하는 문제가 생김
    // OCP, DIP 같은 객체지향 설계 원칙을 준수한 듯 보이지만 사실은 아니다
    // OrderServiceImpl 추상(인터페이스 DiscountPolicy)에 의존하면서 구체(구현 FixDiscountPolicy, RateDiscountPolicy)클래스에도 의존하고 있기 때문
    // 현재 OrderServiceImpl 기능을 확장해서 변경하면 클라이언트 코드에 영향을 주게 되므로 OCP를 위반한 것
    // FixDiscountPolicy를 RateDiscountPolicy로 변경하는 순간 OrderServiceImpl의 소스 코드도 함꼐 변경해야 함 OCP 위반

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // ...
    }
}
```
- 클라이언트 코드가 `DiscountPolicy` 추상화 인터페이스에만 의존하도록 코드를 변경
```java
// 변경 코드
public class OrderServiceImpl implements OrderService {
    private final DiscountPolicy discountPolicy;  // 인터페이스에만 의존하도록 설계, 구현체가 없는데 어떻게 코드를 실행시킬 수 있을까??, 실행 해보면 NPE가 발생
    private final MemberRepository memberRepository;

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
}
```
- 하지만 클라이언트 코드는 인터페이스만으로는 아무것도 실행할 수 없음
- AppConfig가 `FixDiscountPolicy`객체 인스턴스를 클라이언트 코드 대신 생성해서 클라이언트 코드에 의존관계를 주입. 
- DIP원칙을 따르면서 문제도 해결
```java
public class AppConfig {
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy(); // 할인 정책을 변경하고 싶으면 이 구성 영역의 코드만 변경해주면 된다.
        return new RateDiscountPolicy();
    }
}
```
### OCP
**소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야 한다.**
- 다형성 사용하고 클라이언트가 DIP를 지킴
- 애플리케이션을 사용 영역과 구성 영역으로 나눔
- AppConfig가 의존관계를 `FixDiscountPolicy` -> `RateDiscountPolicy`로 변경해서 클라이언트 코드에 주입하므로 클라이언트 코드는 변경하지 않아도 됨
- 소프트웨어 요소를 새롭게 확장해도 사용 영역의 변경은 닫혀 있다.
