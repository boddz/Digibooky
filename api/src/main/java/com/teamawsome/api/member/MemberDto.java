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

    public MemberDto(Member member) {
        this.id = member.getId();
        this.inss = member.getInss();
        this.eMail = member.geteMail();
        this.firstName = member.getFirstName();
        this.lastName = member.getLastName();
        this.streetName = member.getStreetName();
        this.houseNumber = member.getHouseNumber();
        this.postalCode = member.getPostalCode();
        this.city = member.getCity();
    }
}
