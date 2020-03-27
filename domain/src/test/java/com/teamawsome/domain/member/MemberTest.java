package com.teamawsome.domain.member;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    @Test
    public void addNewMember() {
        MemberRepository memberRepository = new MemberRepository();
        memberRepository.addMember(new Member("00000000097", "tom@gm.com", "tom", "dc", "straat", 5, 9000, "Gent"));
        assertEquals(1, memberRepository.getAllMembers().size());
    }

    @Test
    void checkEmail_ifEmailIsValidForm_returnsEmail() {
        Member john = new Member("93051822361", "tom@gm.com", "tom", "dc", "straat", 5, 9000, "Gent");
        assertEquals("tom@gm.com", john.geteMail());
    }

    @Test
    void checkEmail_ifEmailIsNotValidForm_causeOfMissingMonkeyTail_throwException() {
        Assertions.assertThatThrownBy(() -> new Member("93051822361", "tomgm.com", "tom", "dc", "straat", 5, 9000, "Gent"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong form of Email");

    }

    @Test
    void checkEmail_ifEmailIsNotValidForm_causeOfMissingDot_throwException() {
        Assertions.assertThatThrownBy(() -> new Member("93051822361", "tom@gmcom", "tom", "dc", "straat", 5, 9000, "Gent"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong form of Email");

    }

    @Test
    void whenCreatingNewMember_whenInssIsValid_returnInss(){
        Member john = new Member("93051822361", "tom@gm.com", "tom", "dc", "straat", 5, 9000, "Gent");
        assertEquals("93051822361", john.getInss());
    }

    @Test
    void whenCreatingNewMember_whenInssIsNotValid_byNonExistingMonth_throwException(){
        Assertions.assertThatThrownBy(() -> new Member("83130408577", "tom@gmcom", "tom", "dc", "straat", 5, 9000, "Gent"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("This is not a valid inss number.");
    }

    @Test
    void whenCreatingNewMember_whenInssIsNotValid_byFalseControlNumber_throwException(){
        Assertions.assertThatThrownBy(() -> new Member("93051822360", "tom@gmcom", "tom", "dc", "straat", 5, 9000, "Gent"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("This is not a valid inss number.");
    }

}
