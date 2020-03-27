package com.teamawsome.domain.member;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MemberRepository {

    List<Member> memberList = new ArrayList<>();

    public void addMember(Member member) {
        memberList.add(member);
    }

    public boolean isUniqueEmail(String email) {
        return isUnique(email, "email not unique", Member::geteMail);
    }

    public boolean isUniqueInss(String inss) {
        return isUnique(inss, "inss not unique", Member::getInss);
    }

    private boolean isUnique(String varTocheck, String exeptionmessage, Function<Member, String> function) {
        if (!memberList.isEmpty() && listAlreadyContains(varTocheck, function)) {
            throw new NotUniqueException(exeptionmessage);
        }
        return true;
    }

    private boolean listAlreadyContains(String email, Function<Member, String> function) {
        Function<Member, String> geteMail = Member::geteMail;
        return memberList.stream()
                .map(function).noneMatch(x -> x.equals(email));
    }

    public Member getMemberById(int memberId) {
        for (Member member : memberList) {
            if (member.getId() == memberId) {
                return member;
            }
        }
        throw new IllegalArgumentException("Member does not exist");
    }

    public List<Member> getAllMembers() {
        return memberList;
    }
}

