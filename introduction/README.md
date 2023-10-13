# 스프링입문

### DI
> - 필드주입
> - setter주입
> - 생성자 주입
> 
> 이 세가지 방법 중 생성자 주입을 권장

### 스프링에서의 DB 접근 기술

> - 순수 JDBC 방식부터 jdbcTemplate, JPA, spring-data JPA 까지 repository인터페이스의 구현체를 만들어
> - 테스트 코드를 통한 검증까지

> 실무에서 어떤 방식을 권장하는지와  
> 테스트 작성 방법에 대한 내용이 아주 좋다.
> 
> 테스트 코드 내에서 임의로 Exception을 발생시켜 볼 수도 있고,   
> 실제 DB에 접근 할 때 하나의 Transaction을 통해 테스트 하도록해야 commit 하지 않고 반복해서 테스트가 가능하다는 점
> 
> springboot, java8문법, 테스트코드까지 일석삼조의 좋은 강의