package com.teamawsome.api.member;

import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.service.MemberMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

class MemberControllerTest {
    @Test
    void addNewMember_memberIsAddedToRepository() {
        MemberRepository memberRepository = new MemberRepository();
        MemberController memberController = new MemberController(memberRepository, new MemberMapper());
        MemberDto actual = memberController.registerNewMember(new MemberRegistryDTO("00000000097", "tom@gm.com", "tom", "dc", "straat", 5, 9000, "Gent"));
        MemberDto expected = new MemberDto(new Member("00000000097", "tom@gm.com", "tom", "dc", "straat", 5, 9000, "Gent"));
        assertEquals(1, memberRepository.getAllMembers().size());
        assertEquals(expected, actual);

    }

    @Test
    void EmailIsUnique_WhenIsNotUnique_throwNewException() {
        MemberRepository memberRepository = new MemberRepository();
        MemberRegistryDTO member1 = new MemberRegistryDTO("00000000097", "tom@gm.com", "tom", "dc", "straat", 5, 9000, "Gent");
        MemberRegistryDTO member2 = new MemberRegistryDTO("93051822361", "tom@gm.com", "tom", "dc", "straat", 5, 9000, "Gent");
       MemberController memberController = new MemberController(memberRepository,new MemberMapper());
       memberController.registerNewMember(member1);
        Assertions.assertThatThrownBy(() -> memberController.registerNewMember(member2));

        assertEquals(1, memberRepository.getAllMembers().size());

    }

    @Test
    void EmailIsUnique_WhenIsUnique_createNewMember() {
        MemberRepository memberRepository = new MemberRepository();
        Member john = new Member("00000000097", "tom@gm.com", "tom", "dc", "straat", 5, 9000, "Gent");
        memberRepository.addMember(john);
        Member lennon = new Member("06022308177", "to@gm.com", "tom", "dc", "straat", 5, 9000, "Gent");
        memberRepository.addMember(lennon);
        assertEquals(2, memberRepository.getAllMembers().size());

    }
    @Test
    void insslIsUnique_WhenIsNotUnique_throwNewException() {
        MemberRepository memberRepository = new MemberRepository();
        MemberRegistryDTO member1 = new MemberRegistryDTO("00000000097", "tom@gm.com", "tom", "dc", "straat", 5, 9000, "Gent");
        MemberRegistryDTO member2 = new MemberRegistryDTO("00000000097", "to@gm.com", "tom", "dc", "straat", 5, 9000, "Gent");
        MemberController memberController = new MemberController(memberRepository,new MemberMapper());
        memberController.registerNewMember(member1);
        Assertions.assertThatThrownBy(() -> memberController.registerNewMember(member2));

        assertEquals(1, memberRepository.getAllMembers().size());

    }



}