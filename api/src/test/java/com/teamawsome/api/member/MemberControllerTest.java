package com.teamawsome.api.member;

import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.service.MemberMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberControllerTest {
    @Test
    void addNewMember_memberIsAddedToRepository(){
        MemberRepository memberRepository = new MemberRepository();
        MemberController memberController = new MemberController(memberRepository,new MemberMapper());
        MemberDto actual = memberController.registerNewMember(new MemberRegistryDTO("00000000097","tom@gm.com","tom","dc","straat",5,9000,"Gent"));
        MemberDto expected = new MemberDto(new Member("00000000097","tom@gm.com","tom","dc","straat",5,9000,"Gent"));
        assertEquals(1,memberRepository.getAllMembers().size());
        assertEquals(expected,actual);

            }

}