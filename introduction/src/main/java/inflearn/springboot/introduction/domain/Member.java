package inflearn.springboot.introduction.domain;

import javax.persistence.*;

@Entity
public class Member {

    // 데이터를 구분하기 위한, 시스템이 정하는 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // identity전략 db가 알아서 생성해주는 것
    private Long id;

    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
