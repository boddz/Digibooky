package com.teamawsome.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemberRepositoryTest {

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
                .withEmail("thomas@gm.com")
                .withFirstName("tom")
                .withLastName("dc")
                .withStreetName("straat")
                .withHouseNumber(5)
                .withPostalCode(9000)
                .withCity("Gent")
                .build();
        memberRepository.addMember(lennon);
        Assertions.assertThat(memberRepository.getAllMembers()).hasSize(2);
    }


    @Test
    void EmailIsUnique_WhenIsNotUnique_throwNewException() {
        MemberRepository memberRepository = new MemberRepository();
        Member one = Member.MemberBuilder.buildMember()
                .withInss("06022308177")
                .withEmail("tom@gm.com")
                .withFirstName("tom")
                .withLastName("dc")
                .withStreetName("straat")
                .withHouseNumber(5)
                .withPostalCode(9000)
                .withCity("Gent")
                .build();

        Member two = Member.MemberBuilder.buildMember()
                .withInss("68060105329")
                .withEmail("tom@gm.com")
                .withFirstName("tom")
                .withLastName("dc")
                .withStreetName("straat")
                .withHouseNumber(5)
                .withPostalCode(9000)
                .withCity("Gent")
                .build();
        memberRepository.addMember(one);

        Assertions.assertThatExceptionOfType(NotUniqueException.class).isThrownBy(() -> memberRepository.addMember(two));
        Assertions.assertThat(memberRepository.getAllMembers()).hasSize(1);
    }

    @Test
    void insslIsUnique_WhenIsNotUnique_throwNewException() {
        MemberRepository memberRepository = new MemberRepository();
        Member one = Member.MemberBuilder.buildMember()
                .withInss("06022308177")
                .withEmail("tom@gm.com")
                .withFirstName("tom")
                .withLastName("dc")
                .withStreetName("straat")
                .withHouseNumber(5)
                .withPostalCode(9000)
                .withCity("Gent")
                .build();

        Member two = Member.MemberBuilder.buildMember()
                .withInss("06022308177")
                .withEmail("thomas@gm.com")
                .withFirstName("tom")
                .withLastName("dc")
                .withStreetName("straat")
                .withHouseNumber(5)
                .withPostalCode(9000)
                .withCity("Gent")
                .build();
        memberRepository.addMember(one);

        Assertions.assertThatExceptionOfType(NotUniqueException.class).isThrownBy(() -> memberRepository.addMember(two));
        Assertions.assertThat(memberRepository.getAllMembers()).hasSize(1);

    }

    @Test
    void whenRegisteringNewMember_ifLastNameIsNotGiven_throwException(){
        MemberRepository memberRepository = new MemberRepository();
        Member member = Member.MemberBuilder.buildMember()
                .withInss("06022308177")
                .withEmail("thomas@gm.com")
                .withFirstName("tom")
                .withLastName("")
                .withStreetName("straat")
                .withHouseNumber(5)
                .withPostalCode(9000)
                .withCity("Gent")
                .build();
        memberRepository.addMember(member);

        Assertions.assertThatExceptionOfType(NotUniqueException.class).isThrownBy(() -> memberRepository.addMember(member));
    }

    @Test
    void whenRegisteringNewMember_ifCityIsNotGiven_throwException(){
        MemberRepository memberRepository = new MemberRepository();
        Member member = Member.MemberBuilder.buildMember()
                .withInss("06022308177")
                .withEmail("thomas@gm.com")
                .withFirstName("tom")
                .withLastName("")
                .withStreetName("straat")
                .withHouseNumber(5)
                .withPostalCode(9000)
                .withCity("")
                .build();
        memberRepository.addMember(member);

        Assertions.assertThatExceptionOfType(NotUniqueException.class).isThrownBy(() -> memberRepository.addMember(member));
    }

    @Test
    void getListOfMembers_whenNoMembers_returnsEmptyList(){
        //given
        MemberRepository memberRepository = new MemberRepository();
        //then
        Assertions.assertThat(memberRepository.getAllMembers()).isEmpty();
    }

    @Test
    void getListOfMembers_whenOneMember_returnsListWithMember(){
        //given
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
        //then
        Assertions.assertThat(memberRepository.getAllMembers()).containsExactly(john);
    }
}
