package com.teamawsome.domain.member;

public class Member {
    private final int id;
    private final int inss;
    private String eMail;
    private final String firstName;
    private final String lastName;
    private String streetName;
    private int houseNumber;
    private int postalCode;
    private String city;
    private int counter = 100;

    public Member(int inss, String eMail, String firstName, String lastName, String streetName, int houseNumber, int postalCode, String city) {
        this.id = counter;
        counter++;
        this.inss = checkIfValidInss(inss);
        this.eMail = checkIfValidEmail(eMail);
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
    }

    private String checkIfValidEmail(String eMail) {
        String regex = "(.*)(\\@)(.*)(\\.)(.*)";
        if (!eMail.matches(regex))
            throw new IllegalArgumentException("Wrong form of Email");
        return eMail;
    }

    private int checkIfValidInss(int inss) {   // to be implemented
        return inss;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getInss() {
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

    public int getId() {
        return id;
    }
}

