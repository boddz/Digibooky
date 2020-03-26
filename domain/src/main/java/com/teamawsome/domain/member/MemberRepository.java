package com.teamawsome.domain.member;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemberRepository {
    List<Member> memberList = new ArrayList<>();

    public List<Member> getAllMembers() {
        return memberList;
    }

    public void addMember(Member member) {
        memberList.add(member);

    }
}

