# 스프링 데이터 엑세스

### H2 데이터베이스
> - 개발이나 테스트 용도로 가볍고 편리한 DB, 웹 화면 제공
> - https://www.h2database.com
> - 순수 JDBC
> - JPA : 객체를 쿼리없이 DB에 저장
> - 스프링 데이터 JPA : JPA를 편리하게 사용할 수 있도록 한 번 감싼 기술


### 순수 JDBC
> - 고대 선배님들의 방식 ..
> - build.gradle 파일에 jdbc, h2 db관련 라이브러리 추가
> ```text
>    implementation 'org.springframework.boot:spring-boot-starter-jdbc'
>    runtimeOnly 'com.h2database:h2'
>```
>
> - 객체지향 설계!
> - 다형성 이용 interface를 구현하는 구현체만 변경해서 바꿔 끼면 됨
> - 스프링은 이런 설계를 편하게 할 수 있음
> - 개방 폐쇄 원칙(OCP, Open-Closed Principle)
>   - 확장에는 열려있고, 수정, 변경에는 닫혀있다.
>   - 인터페이스 기반의 어떤 다형성, 꼭 인터페이스가 아니더라도 객체지향언어에서 말하는 다형성을 잘 이용하면 실제 애플리케이션을 동작하는 코드를 하나도 변경하지 않아도 됨 

### 통합테스트
> - @SpringBootTest : 스프링 컨테이너와 테스트를 함께 실행
> - @Transactional : 테스트 케이스에 이 애노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백 함, 이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않음
>
> 순수한 단위 테스트가 훨씬 좋은 테스트 일 확률 이 높음
> 스프링컨테이너 없이 단위테스트 하는 연습을 해야 함

### 스프링 JdbcTemplate
> - 순수 jdbc와 동일한 환경설정
> - 스프링 JdbcTemplate과 MyBatis같은 라이브러리는 JDBC API에서 본 반복 코드를 대부분 제거 해 줌, 하지만 SQL은 직접 작성해야 함
> - 실무에서도 많이 사용함



>> 실무에서 항상 테스트 코드를 잘 짜려고 노려해보자!
>> 프로덕션이 커지면 테스트 코드 잘 짜는게 정말정말 중요!


### JPA(Java Persistence API)
> - 기존의 반복 코드는 물론, 기본적인 SQL도 JPA가 직접 만들어서 실행
> - SQL과 데이터 중심의 설계에서 객체 중심의 설계로 패러다임을 전환 할 수 있음
> - 개발 생산성을 크게 높일 수 있음
> - 스프링만큼 넓이와 깊이가 있는 기술 ...
> - JPA는 인터페이스, 거의 Hibernate를 구현체로 사용
> - ORM!
> - 트래픽이 크거나 비즈니스 금액이 큰 서비스에 슬 수 있을까?
> - 해외에서는 은행에서도 사용하고, 조 단위의 거래하는 결제시스템, 정산시스템, 주문시스템 다 잘 되고 있음!

### 스프링 데이터 JPA
> - 스프링 부트와 JPA만 사용해도 개발 생산성이 정말 많이 증가, 개발해야할 코드도 확연히 줄어듬!
> - 마법처럼 인터페이스만으로도 개발을 할 수 있다!
> - 반복 개발해 온 기본 CRUD 기능도 스프링 데이터 JPA가 모두 제공해 줌!
> - 실무에서 관계형 데이터베이스를 사용한다면 스프링 데이터 JPA는 이제 선택이 아니라 필 수!!
> - 주의 : 스프링 데이터 JPA는 JPA를 편리하게 사용하도록 도와주는 기술. JPA를 먼저 익힌 후 학습 할 것!
> - 공통화된 비지니스 메소드는 다 정의되어 있음!
> - 공통화 할 수 없는 메소드의 경우 규칙에 맞게 메소드명을 만들면 리플렉션 기술로 읽어서 풀어 냄
> - 제공 기능
>   - 인터페이스를 통한 기본적인 CRUD
>   - findByName(), findByEmail()처럼 메서드 이름 만으로 조회 기능 제공
>   - 페이징 기능 자동 제공
> - 참고 : 실무에서는 JPA와 스프링 데이터 JPA를 기보능로 사용하고, 복잡한 쿼리는 Querydsl이라는 라이브러리를 사용
> - Querydsl을 사용하면 쿼리도 자바 코드로 안전하게 작성할 수 있고, 동적 쿼리도 편리하게 작성할 수 있음
> - 이 조합으로 해결하기 어려운 쿼리는 JPA가 제공하는 네이티브 쿼리를 사용하거나, jdbcTemplate을 사용하면 됨!