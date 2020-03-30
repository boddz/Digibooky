package com.teamawsome.api.member;

import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.infrastructure.authentication.external.ExternalAuthentication;
import com.teamawsome.infrastructure.authentication.external.FakeAuthenticationService;
import com.teamawsome.infrastructure.authentication.feature.BookstoreRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Base64Utils;


import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest {

    @Test
    void addNewMember_memberIsAddedToRepository() {
        MemberRepository memberRepository = new MemberRepository();
        MemberController memberController = new MemberController(memberRepository, new MemberMapper(), new FakeAuthenticationService(new ArrayList<>()));
        MemberDto actual = memberController.registerNewMember( new MemberRegistryDTO("00000000097", "tom@gm.com", "tom", "dc", "straat", 5, 9000, "Gent"));
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
       memberController.registerNewMember(member1);
        Assertions.assertThatThrownBy(() -> memberController.registerNewMember(member2));

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
        memberController.registerNewMember(member1);
        Assertions.assertThatThrownBy(() -> memberController.registerNewMember(member2));

        assertEquals(1, memberRepository.getAllMembers().size());

    }

    @Test
    void whenRegisteringNewMember_ifLastNameIsNotGiven_throwException(){
        MemberController memberController = new MemberController(new MemberRepository(),new MemberMapper(), new FakeAuthenticationService(new ArrayList<>()));
        MemberRegistryDTO member1 = new MemberRegistryDTO("00000000097", "tom@gm.com", "tom", "", "straat", 5, 9000, "Gent");
        Assertions.assertThatThrownBy(() -> memberController.registerNewMember(member1));
    }

    @Test
    void whenRegisteringNewMember_ifCityIsNotGiven_throwException(){
        MemberController memberController = new MemberController(new MemberRepository(),new MemberMapper(), new FakeAuthenticationService(new ArrayList<>()));
        MemberRegistryDTO member1 = new MemberRegistryDTO("00000000097", "tom@gm.com", "tom", "d", "straat", 5, 9000, "");
        Assertions.assertThatThrownBy(() -> memberController.registerNewMember(member1));
    }


    @Test
    void addNewAdmin_memberIsAddedToRepository() {
        MemberRepository memberRepository = new MemberRepository();
        FakeAuthenticationService fakeAuthenticationService1 = new FakeAuthenticationService(new ArrayList<>());
        MemberController memberController = new MemberController(memberRepository, new MemberMapper(), fakeAuthenticationService1);
        memberController.registerNewMember( new MemberRegistryDTO("00000000097", "tom@gm.com", "tom", "dc", "straat", 5, 9000, "Gent"));
        assertEquals(fakeAuthenticationService1.getUser("admin", "admin"), ExternalAuthentication.externalAuthentication().withUsername("admin").withPassword("admin").withRoles(List.of(BookstoreRole.ADMIN)));
    }

    @Test
    void addNewLibrarian_memberIsAddedToRepository() {
        MemberRepository memberRepository = new MemberRepository();
        FakeAuthenticationService fakeAuthenticationService1 = new FakeAuthenticationService(new ArrayList<>());
        MemberController memberController = new MemberController(memberRepository, new MemberMapper(), fakeAuthenticationService1);
        memberController.registerNewMember( new MemberRegistryDTO("00000000097", "tom@gm.com", "tom", "dc", "straat", 5, 9000, "Gent"));
        assertEquals(fakeAuthenticationService1.getUser("librarian", "librarian"), ExternalAuthentication.externalAuthentication().withUsername("librarian").withPassword("librarian").withRoles(List.of(BookstoreRole.LIBRARIAN)));

    }

    @Test
    void getListOfMembers_whenNoMembers_returnsEmptyList(){
        //given
        MemberRepository memberRepository = new MemberRepository();
        MemberMapper memberMapper = new MemberMapper();
        FakeAuthenticationService fakeAuthenticationService = new FakeAuthenticationService(null);
        MemberController memberController = new MemberController(memberRepository,memberMapper,fakeAuthenticationService);
        //then
        Assertions.assertThat(memberController.getListOfMembers()).isEmpty();
    }

    @Test
    void getListOfMembers_whenOneMember_returnsListWithMember(){
        //given
        MemberRepository memberRepository = new MemberRepository();
        MemberMapper memberMapper = new MemberMapper();
        FakeAuthenticationService fakeAuthenticationService = new FakeAuthenticationService(null);
        MemberController memberController = new MemberController(memberRepository,memberMapper,fakeAuthenticationService);
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
        MemberDto expectedResult = new MemberDto(john);
        expectedResult.setInssToEmpty();
        //then
        Assertions.assertThat(memberController.getListOfMembers()).containsExactly(expectedResult);
    }


//    @Test
//    void checkIfSpringBootIsCorrectlyConfigured() {
//        // Just an empty test ... Impossible to make it easier!
//        // Needs @SpringBootTest + 1 (empty) @Test
//        // This test will fail when there are 'obvious' problems with the Spring application context, like a missing bean.
//    }

//    @Autowired
//    WebTestClient webTestClient;
//
//    @Test
//    void getAllMembers_givesCorrectResponse(){
//        //given
//        //WebTestClient webTestClient = WebTestClient.bindToController(MemberController.class).build();
//        String url = "/members";
//
//        //when
//        webTestClient
//                .get()
//                .uri(url)
//                .header("Authorization", "Basic " + Base64Utils
//                        .encodeToString(("admin" + ":" + "admin").getBytes(UTF_8)))
//                .exchange()
//                .expectStatus().isOk()
//                .expectBodyList(MemberDto.class)
//                .hasSize(0);
//    }




//    @Test
//    void addWrongPathvariable() {
//        MemberRepository memberRepository = new MemberRepository();
//        FakeAuthenticationService fakeAuthenticationService1 = new FakeAuthenticationService(new ArrayList<>());
//        MemberController memberController = new MemberController(memberRepository, new MemberMapper(), fakeAuthenticationService1);
//     Assertions.assertThatThrownBy(()-> memberController.registerNewMember( new MemberRegistryDTO("00000000097", "tom@gm.com", "tom", "dc", "straat", 5, 9000, "Gent"), "onzin"));
//
//    }

}