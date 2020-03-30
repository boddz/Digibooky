package com.teamawsome.domain.service;

import com.teamawsome.domain.dto.MemberDto;
import com.teamawsome.domain.dto.MemberRegistryDTO;
import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.infrastructure.authentication.external.ExternalAuthentication;
import com.teamawsome.infrastructure.authentication.external.FakeAuthenticationService;
import com.teamawsome.infrastructure.authentication.feature.BookstoreRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberManagementTest {
    FakeAuthenticationService fakeAuthenticationService = Mockito.mock(FakeAuthenticationService.class);
    MemberRepository memberRepository = Mockito.mock(MemberRepository.class);
    MemberMapper memberMapper = new MemberMapper();
    MemberManagement memberManagement = new MemberManagement(fakeAuthenticationService, memberRepository, memberMapper);

    @Test
    void addNewMember_memberIsAddedToRepository() {
        MemberDto actual = memberManagement.registerMember(
                new MemberRegistryDTO("00000000097", "tom@gm.com", "tom", "dc", "straat",
                        5, 9000, "Gent", "secret"));
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

        MemberDto expected = memberMapper.toMemberDto(expectedMember);
        assertEquals(expected, actual);

    }

    @Test
    public void checkIfValidInputThrowsOnInvalidInput(){
        Mockito.when(memberRepository.isUniqueEmail(Mockito.anyString())).thenReturn(true);
        Mockito.when(memberRepository.isUniqueInss(Mockito.anyString())).thenReturn(true);

        MemberRegistryDTO memberRegistryDTO = new MemberRegistryDTO("123456789","test@example.com","Jos",
                "","VerplaatStraat",10,1000, "", "secret");
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> memberManagement.checkIfValidInput(memberRegistryDTO));
    }

}
