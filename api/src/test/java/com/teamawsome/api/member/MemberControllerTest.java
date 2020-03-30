package com.teamawsome.api.member;

import com.teamawsome.domain.dto.MemberDto;
import com.teamawsome.domain.service.MemberMapper;
import com.teamawsome.domain.dto.MemberRegistryDTO;
import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.infrastructure.authentication.external.ExternalAuthentication;
import com.teamawsome.infrastructure.authentication.external.FakeAuthenticationService;
import com.teamawsome.infrastructure.authentication.feature.BookstoreRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest {





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