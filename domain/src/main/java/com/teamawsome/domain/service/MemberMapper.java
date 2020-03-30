package com.teamawsome.domain.service;

import com.teamawsome.domain.dto.MemberDto;
import com.teamawsome.domain.member.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public MemberDto toMemberDto(Member member) {
        return new MemberDto(member.getId(), member.getInss(), member.geteMail(), member.getFirstName(),
        member.getLastName(), member.getStreetName(), member.getHouseNumber(), member.getPostalCode(),
        member.getCity());
    }
}
