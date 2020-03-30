package com.teamawsome.domain.member;

import com.teamawsome.domain.dto.MemberRegistryDTO;
import com.teamawsome.domain.service.MemberManagement;
import com.teamawsome.domain.service.MemberMapper;
import com.teamawsome.infrastructure.authentication.external.ExternalAuthentication;
import com.teamawsome.infrastructure.authentication.external.FakeAuthenticationService;
import com.teamawsome.infrastructure.authentication.feature.BookstoreRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MemberAuthenticationIntegrationTest {

    @Test
    public void addNewAdmin_memberIsAddedToRepository() {
        FakeAuthenticationService fakeAuthenticationService = new FakeAuthenticationService(new ArrayList<>());
        MemberRepository memberRepository = new MemberRepository();
        MemberMapper memberMapper = new MemberMapper();
        MemberManagement memberManagement = new MemberManagement(fakeAuthenticationService, memberRepository, memberMapper);
        memberManagement.registerAdministrator(
                new MemberRegistryDTO("00000000097", "tom@gm.com", "tom", "dc",
                        "straat", 5, 9000, "Gent","secret"));
        ExternalAuthentication expected = ExternalAuthentication.externalAuthentication()
                                            .withUsername("tom")
                                            .withPassword("secret")
                                            .withRoles(List.of(BookstoreRole.ADMIN));
        Assertions.assertThat(fakeAuthenticationService.getUser("tom", "secret")).isEqualTo(expected);
    }

    @Test
    public void addNewLibrarian_memberIsAddedToRepository() {
        FakeAuthenticationService fakeAuthenticationService = new FakeAuthenticationService(new ArrayList<>());
        MemberRepository memberRepository = new MemberRepository();
        MemberMapper memberMapper = new MemberMapper();
        MemberManagement memberManagement = new MemberManagement(fakeAuthenticationService, memberRepository, memberMapper);
        memberManagement.registerLibrarian(
                new MemberRegistryDTO("00000000097", "tom@gm.com", "tom", "dc",
                        "straat", 5, 9000, "Gent","secret"));
        ExternalAuthentication expected = ExternalAuthentication.externalAuthentication()
                .withUsername("tom")
                .withPassword("secret")
                .withRoles(List.of(BookstoreRole.LIBRARIAN));
        Assertions.assertThat(fakeAuthenticationService.getUser("tom", "secret")).isEqualTo(expected);
    }
}
