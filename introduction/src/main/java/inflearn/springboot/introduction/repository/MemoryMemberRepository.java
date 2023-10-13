package inflearn.springboot.introduction.repository;

import inflearn.springboot.introduction.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class MemoryMemberRepository implements MemberRepository {
    // 실무에서 아직 데이터 저장소가 선정되지 않았을 경우(가상의 시나리오)
    // 기능정의를 해 둔 interface를 만들고
    // 메모리 기능을 하는 Map을 만들고
    // 테스트 케이스 작성!
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // null이 반환 될 가능성이 있는 경우 사용
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
