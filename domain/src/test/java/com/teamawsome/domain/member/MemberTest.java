package com.teamawsome.domain.member;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    @Test
    public void addNewMember(){
        MemberRepository memberRepository = new MemberRepository();
        memberRepository.addMember(new Member(83,"tom@gm.com","tom","dc","straat",5,9000,"Gent"));
        assertEquals(1,memberRepository.getAllMembers().size());
    }


}