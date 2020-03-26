package com.teamawsome.api.member;

import com.teamawsome.domain.member.Member;

public class MemberDto {
    private  int id;
    private  int inss;
    private  String eMail;
    private  String firstName;
    private  String lastName;
    private String streetName;
    private int houseNumber;
    private int postalCode;
    private String city;

    public MemberDto(int id, int inss, String eMail, String firstName, String lastName, String streetName, int houseNumber, int postalCode, String city) {
        this.id = id;
        this.inss = inss;
        this.eMail = eMail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
    }
}
