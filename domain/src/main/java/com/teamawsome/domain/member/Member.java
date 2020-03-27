package com.teamawsome.domain.member;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

public class Member {
    private final int id;
    private final String inss;
    private String eMail;
    private final String firstName;
    private final String lastName;
    private String streetName;
    private int houseNumber;
    private int postalCode;
    private String city;
    private int counter = 100;


    public Member(String inss, String eMail, String firstName, String lastName, String streetName, int houseNumber, int postalCode, String city) {
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

    private String checkIfValidEmail(String email) {
        String regex = ".*\\@.*\\..*";
        if (!email.matches(regex)) {
            throw new IllegalArgumentException("Wrong form of Email");
        }
        return eMail;
    }

    private String checkIfValidInss(String inss) {
        String regex = "^[0-9][0-9](1[0-2]||0[0-9])(3[01]||[012][0-9])[0-9]{5}$";
        if (inss.matches(regex) && checkInssControlNumber(inss)) {
            return inss;
        }
        throw new IllegalArgumentException("This is not a valid inss number.");
    }

    private boolean checkInssControlNumber(String inss) {
        int number = Integer.parseInt(inss.substring(0, 9));
        int controlNumber = Integer.parseInt(inss.substring((9)));
        int moduloOfNumber = number % 97;
        int calculatedControlNumber = 97 - moduloOfNumber;
        return calculatedControlNumber == controlNumber;
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

    public int getId() {
        return id;
    }
}

