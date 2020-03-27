package com.teamawsome.domain.member;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberRepository {
    List<Member> memberList = new ArrayList<>();

    public List<Member> getAllMembers() {
        return memberList;
    }

    public void addMember(Member member) {
        memberList.add(member);
    }

    public boolean isUniqueEmail(String email) {
        if (memberList.isEmpty()) {
            return true;
        }
        return memberList.stream()
                .map(Member::geteMail)
                .filter(x -> x.equals(email))
                .collect(Collectors.toList()).isEmpty();
    }

    public Member getMemberById(int memberId) {
        for (Member member : memberList){
            if(member.getId()== memberId){
                return member;
            }
        }
        throw new IllegalArgumentException("Member does not exist");
    }
}

