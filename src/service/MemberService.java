package service;

import model.Member;
import util.DataStore;

public class MemberService {

    public Member register(String name, String email, String password) {
        // Ensure email uniqueness
        boolean exists = DataStore.members.stream()
                .anyMatch(m -> m.getEmail().equalsIgnoreCase(email));
        if (exists) return null;

        String id = "M" + (++DataStore.memberCounter);
        Member member = new Member(id, name, email, password);
        DataStore.members.add(member);
        return member;
    }

    public Member authenticate(String email, String password) {
        return DataStore.members.stream()
                .filter(m -> m.getEmail().equalsIgnoreCase(email)
                        && m.getPassword().equals(password))
                .findFirst().orElse(null);
    }

    public Member findById(String memberId) {
        return DataStore.members.stream()
                .filter(m -> m.getMemberId().equalsIgnoreCase(memberId))
                .findFirst().orElse(null);
    }

    public boolean updateMember(String memberId, String name, String email, String password) {
        Member m = findById(memberId);
        if (m == null) return false;
        m.setName(name);
        m.setEmail(email);
        m.setPassword(password);
        return true;
    }
}