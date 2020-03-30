package com.teamawsome.domain.dto;

public class MemberRegistryDTO {
    private String inss;
    private String eMail;
    private String firstName;
    private String lastName;
    private String streetName;
    private int houseNumber;
    private int postalCode;
    private String city;

    public MemberRegistryDTO(String inss, String eMail, String firstName, String lastName, String streetName, int houseNumber, int postalCode, String city) {
        this.inss = inss;
        this.eMail = eMail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getInss() {
        return inss;
    }

    public String geteMail() {
        return eMail;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStreetName() {
        return streetName;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }
}
