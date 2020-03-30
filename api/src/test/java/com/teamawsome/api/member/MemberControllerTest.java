package com.teamawsome.api.member;

import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.infrastructure.authentication.external.ExternalAuthentication;
import com.teamawsome.infrastructure.authentication.external.FakeAuthenticationService;
import com.teamawsome.infrastructure.authentication.feature.BookstoreRole;
import com.teamawsome.service.MemberMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberControllerTest {
    @Test
    void addNewMember_memberIsAddedToRepository() {
        MemberRepository memberRepository = new MemberRepository();
        MemberController memberController = new MemberController(memberRepository, new MemberMapper(), new FakeAuthenticationService(new ArrayList<>()));
        MemberDto actual = memberController.registerNewUser( new MemberRegistryDTO("00000000097", "tom@gm.com", "tom", "dc", "straat", 5, 9000, "Gent"), "member");
        Member expectedMember = Member.MemberBuilder.buildMember()
                .withInss("00000000097")
                .withEmail("tom@gm.com")
                .withFirstName("tom")
                .withLastName("dc")
                .withStreetName("straat")
                .withHouseNumber(5)
                .withPostalCode(9000)
                .withCity("Gent")
                .build();
        MemberDto expected = new MemberDto(expectedMember);
        assertEquals(1, memberRepository.getAllMembers().size());
        assertEquals(expected, actual);

    }

    @Test
    void EmailIsUnique_WhenIsNotUnique_throwNewException() {
        MemberRepository memberRepository = new MemberRepository();
        MemberRegistryDTO member1 = new MemberRegistryDTO("00000000097", "tom@gm.com", "tom", "dc", "straat", 5, 9000, "Gent");
        MemberRegistryDTO member2 = new MemberRegistryDTO("93051822361", "tom@gm.com", "tom", "dc", "straat", 5, 9000, "Gent");
       MemberController memberController = new MemberController(memberRepository,new MemberMapper(), new FakeAuthenticationService(new ArrayList<>()));
       memberController.registerNewUser(member1, "member");
        Assertions.assertThatThrownBy(() -> memberController.registerNewUser(member2, "member"));

        assertEquals(1, memberRepository.getAllMembers().size());

    }

    @Test
    void EmailIsUnique_WhenIsUnique_createNewMember() {
        MemberRepository memberRepository = new MemberRepository();
        Member john = Member.MemberBuilder.buildMember()
                .withInss("00000000097")
                .withEmail("tom@gm.com")
                .withFirstName("tom")
                .withLastName("dc")
                .withStreetName("straat")
                .withHouseNumber(5)
                .withPostalCode(9000)
                .withCity("Gent")
                .build();
        memberRepository.addMember(john);
        Member lennon = Member.MemberBuilder.buildMember()
                .withInss("06022308177")
                .withEmail("tom@gm.com")
                .withFirstName("tom")
                .withLastName("dc")
                .withStreetName("straat")
                .withHouseNumber(5)
                .withPostalCode(9000)
                .withCity("Gent")
                .build();
        memberRepository.addMember(lennon);
        assertEquals(2, memberRepository.getAllMembers().size());

    }
    @Test
    void insslIsUnique_WhenIsNotUnique_throwNewException() {
        MemberRepository memberRepository = new MemberRepository();
        MemberRegistryDTO member1 = new MemberRegistryDTO("00000000097", "tom@gm.com", "tom", "dc", "straat", 5, 9000, "Gent");
        MemberRegistryDTO member2 = new MemberRegistryDTO("00000000097", "to@gm.com", "tom", "dc", "straat", 5, 9000, "Gent");
        MemberController memberController = new MemberController(memberRepository,new MemberMapper() , new FakeAuthenticationService(new ArrayList<>()));
        memberController.registerNewUser(member1, "member");
        Assertions.assertThatThrownBy(() -> memberController.registerNewUser(member2, "member"));

        assertEquals(1, memberRepository.getAllMembers().size());

    }

    @Test
    void whenRegisteringNewMember_ifLastNameIsNotGiven_throwException(){
        MemberController memberController = new MemberController(new MemberRepository(),new MemberMapper(), new FakeAuthenticationService(new ArrayList<>()));
        MemberRegistryDTO member1 = new MemberRegistryDTO("00000000097", "tom@gm.com", "tom", "", "straat", 5, 9000, "Gent");
        Assertions.assertThatThrownBy(() -> memberController.registerNewUser(member1, "member"));
    }

    @Test
    void whenRegisteringNewMember_ifCityIsNotGiven_throwException(){
        MemberController memberController = new MemberController(new MemberRepository(),new MemberMapper(), new FakeAuthenticationService(new ArrayList<>()));
        MemberRegistryDTO member1 = new MemberRegistryDTO("00000000097", "tom@gm.com", "tom", "d", "straat", 5, 9000, "");
        Assertions.assertThatThrownBy(() -> memberController.registerNewUser(member1, "member"));
    }


    @Test
    void addNewAdmin_memberIsAddedToRepository() {

        MemberRepository memberRepository = new MemberRepository();
        FakeAuthenticationService fakeAuthenticationService1 = new FakeAuthenticationService(new ArrayList<>());
        MemberController memberController = new MemberController(memberRepository, new MemberMapper(), fakeAuthenticationService1);
        memberController.registerNewUser( new MemberRegistryDTO("00000000097", "tom@gm.com", "tom", "dc", "straat", 5, 9000, "Gent"), "admin");
        assertEquals(fakeAuthenticationService1.getUser("tom", "admin"), ExternalAuthentication.externalAuthentication().withUsername("tom").withPassword("admin").withRoles(List.of(BookstoreRole.ADMIN)));
    }

    @Test
    void addNewLibrarian_memberIsAddedToRepository() {
        MemberRepository memberRepository = new MemberRepository();
        FakeAuthenticationService fakeAuthenticationService1 = new FakeAuthenticationService(new ArrayList<>());
        MemberController memberController = new MemberController(memberRepository, new MemberMapper(), fakeAuthenticationService1);
        memberController.registerNewUser( new MemberRegistryDTO("00000000097", "tom@gm.com", "tom", "dc", "straat", 5, 9000, "Gent"), "librarian");
        assertEquals(fakeAuthenticationService1.getUser("tom", "librarian"), ExternalAuthentication.externalAuthentication().withUsername("tom").withPassword("librarian").withRoles(List.of(BookstoreRole.LIBRARIAN)));

    }

    @Test
    void addWrongPathvariable() {
        MemberRepository memberRepository = new MemberRepository();
        FakeAuthenticationService fakeAuthenticationService1 = new FakeAuthenticationService(new ArrayList<>());
        MemberController memberController = new MemberController(memberRepository, new MemberMapper(), fakeAuthenticationService1);
     Assertions.assertThatThrownBy(()-> memberController.registerNewUser( new MemberRegistryDTO("00000000097", "tom@gm.com", "tom", "dc", "straat", 5, 9000, "Gent"), "onzin"));

    }

}