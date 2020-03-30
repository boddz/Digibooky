package com.teamawsome.domain.member;


import com.teamawsome.domain.member.Member.MemberBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    @Test
    public void addNewMember() {
        MemberRepository memberRepository = new MemberRepository();
        Member member = MemberBuilder.buildMember()
                .withInss("00000000097")
                .withEmail("tom@gm.com")
                .withFirstName("tom")
                .withLastName("dc")
                .withStreetName("straat")
                .withHouseNumber(5)
                .withPostalCode(9000)
                .withCity("Gent")
                .build();
        memberRepository.addMember(member);
        assertEquals(1, memberRepository.getAllMembers().size());
    }

    @Test
    void checkEmail_ifEmailIsValidForm_returnsEmail() {
        Member john = MemberBuilder.buildMember()
                .withInss("93051822361")
                .withEmail("tom@gm.com")
                .withFirstName("tom")
                .withLastName("dc")
                .withStreetName("straat")
                .withHouseNumber(5)
                .withPostalCode(9000)
                .withCity("Gent")
                .build();
        assertEquals("tom@gm.com", john.geteMail());
    }

    @Test
    void checkEmail_ifEmailIsNotValidForm_causeOfMissingMonkeyTail_throwException() {
        MemberBuilder builder = MemberBuilder.buildMember()
                .withInss("93051822361")
                .withEmail("tomgm.com")
                .withFirstName("tom")
                .withLastName("dc")
                .withStreetName("straat")
                .withHouseNumber(5)
                .withPostalCode(9000)
                .withCity("Gent");
        Assertions.assertThatThrownBy(builder::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong form of Email");

    }

    @Test
    void checkEmail_ifEmailIsNotValidForm_causeOfMissingDot_throwException() {
        MemberBuilder builder = MemberBuilder.buildMember()
                .withInss("93051822361")
                .withEmail("tom@gmcom")
                .withFirstName("tom")
                .withLastName("dc")
                .withStreetName("straat")
                .withHouseNumber(5)
                .withPostalCode(9000)
                .withCity("Gent");
        Assertions.assertThatThrownBy(builder::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong form of Email");

    }

    @Test
    void whenCreatingNewMember_whenInssIsValid_returnInss() {
        Member john = MemberBuilder.buildMember()
                .withInss("93051822361")
                .withEmail("tom@gm.com")
                .withFirstName("tom")
                .withLastName("dc")
                .withStreetName("straat")
                .withHouseNumber(5)
                .withPostalCode(9000)
                .withCity("Gent")
                .build();
        assertEquals("93051822361", john.getInss());
    }

    @Test
    void whenCreatingNewMember_whenInssIsNotValid_byNonExistingMonth_throwException() {
        MemberBuilder builder = MemberBuilder.buildMember()
                .withInss("83130408577")
                .withEmail("tom@gmcom")
                .withFirstName("tom")
                .withLastName("dc")
                .withStreetName("straat")
                .withHouseNumber(5)
                .withPostalCode(9000)
                .withCity("Gent");
        Assertions.assertThatThrownBy(builder::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("This is not a valid inss number.");
    }

    @Test
    void whenCreatingNewMember_whenInssIsNotValid_byFalseControlNumber_throwException() {
        MemberBuilder builder = MemberBuilder.buildMember()
                .withInss("93051822360")
                .withEmail("tom@gmcom")
                .withFirstName("tom")
                .withLastName("dc")
                .withStreetName("straat")
                .withHouseNumber(5)
                .withPostalCode(9000)
                .withCity("Gent");
        Assertions.assertThatThrownBy(builder::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("This is not a valid inss number.");
    }


}
