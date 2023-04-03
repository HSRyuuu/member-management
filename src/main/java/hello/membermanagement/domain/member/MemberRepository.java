package hello.membermanagement.domain.member;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class MemberRepository {
    private static final Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 1000L; // Id

    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(),member);
        return member;
    }
    public Member findById(Long id){
        return store.get(id);
    }
    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }
    public void updateMember(Long memberId, Member updateParam){
        Member findMember = findById(memberId);
        findMember.setName(updateParam.getName());
        findMember.setAge(updateParam.getAge());
        findMember.setBirthday(updateParam.getBirthday());
        findMember.setPhoneNumber(updateParam.getPhoneNumber());
        findMember.setEmail(updateParam.getEmail());
    }
    public void deleteMember(Long id){
        store.remove(id);
    }

    public boolean isExist(Long id){
        if(store.containsKey(id))return true;
        else return false;
    }

    /**
     * 테스트용 전체 삭제 메소드
     */
    public void clearStore(){
        store.clear();
    }

}
