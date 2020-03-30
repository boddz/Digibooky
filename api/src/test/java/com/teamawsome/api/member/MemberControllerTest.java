package com.teamawsome.api.member;

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