package hello.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();
    // static 영역에 객체 instance를 미리 하나 생성해서 올려 둠
    // 자기자신을 내부에 private static 으로 가지고 있으면 클래스 레벨에 딱 하나만 올라감
    // jvm이 뜰 때, 스태틱 영역에 new연산자를 사용한 것을 보고 내부적으로 실행해서 자기자신 객체를 생성해서 인스턴스에 참조를 넣어 둠

    public static SingletonService getInstance() {  //  이 객체 인스턴스가 필요하면 오직 getInstance() 메서드를 통해서만 조회할 수 있고, 이 메서드를 호출하면 항상 같은 인스턴스를 반환!
        return instance;
    }

//    생성자를 private 레벨로 만들어 외부에서 new 연산자를 통한 객체 생성을 막음
    private SingletonService() {

    }

    public void login() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}

class SingletonConstructorTest {
    public static void main(String[] args) {
//        SingletonService singletonService = new SingletonService();
    }
}
