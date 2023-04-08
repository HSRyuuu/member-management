package hello.membermanagement.domain.admin;

import hello.membermanagement.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AdminRepository {
    private static Map<String, Admin> store = new HashMap<>();

    public Admin save(Admin admin){
        store.put(admin.getId(),admin);
        return admin;
    }

    public Admin findById(String id){
        return store.get(id);
    }
    public List<Admin> findAll(){
        return new ArrayList<>(store.values());
    }
    public Optional<Admin> findByLoginId(String loginId){
        return findAll().stream()
                .filter(admin -> admin.getLoginId().equals(loginId))
                .findFirst();
    }
    public void clearStore(){
        store.clear();
    }

}
