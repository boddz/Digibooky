package com.teamawsome.domain.member;

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
    private static int counter = 100;

/*
    private Member(String inss, String eMail, String firstName, String lastName, String streetName, int houseNumber, int postalCode, String city) {
        this.id = counter++;
        this.inss = checkIfValidInss(inss);
        this.eMail = checkIfValidEmail(eMail);
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
    }*/

    private Member(MemberBuilder builder){
        this.id = counter++;
        this.inss = checkIfValidInss(builder.inss);
        this.eMail = checkIfValidEmail(builder.eMail);
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.streetName = builder.streetName;
        this.houseNumber = builder.houseNumber;
        this.postalCode = builder.postalCode;
        this.city = builder.city;
    }

    private String checkIfValidEmail(String email) {
        String regex = ".*\\@.*\\..*";
        if (!email.matches(regex)) {
            throw new IllegalArgumentException("Wrong form of Email");
        }
        return email;
    }

    private String checkIfValidInss(String inss) {
        String regex = "^[0-9][0-9](1[0-2]||0[0-9])(3[01]||[012][0-9])[0-9]{5}$";
        if (inss.matches(regex) && checkInssControlNumber(inss)) {
            return inss;
        }
        throw new IllegalArgumentException("This is not a valid inss number.");
    }

    private boolean checkInssControlNumber(String inss) {
        long number = Long.parseLong(inss.substring(0, 9));
        long numberChecked = checkIfBornAfter1999(number);
        long controlNumber = Long.parseLong(inss.substring((9)));
        long moduloOfNumber = numberChecked % 97;
        long calculatedControlNumber = 97 - moduloOfNumber;
        return calculatedControlNumber == controlNumber;
    }

    private long checkIfBornAfter1999(long number) {
        long checkedNumber;
        if (number < 99999999L && number > 1) {
            checkedNumber = number + 2000000000L;
        } else {
            checkedNumber = number;
        }
        return checkedNumber;
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


    /*
    Builder
     */

    public static class MemberBuilder {
        private String inss;
        private String eMail;
        private String firstName;
        private String lastName;
        private String streetName;
        private int houseNumber;
        private int postalCode;
        private String city;

        public static MemberBuilder buildMember() {
            return new MemberBuilder();
        }

        public MemberBuilder withInss(String inss) {
            this.inss = inss;
            return this;
        }
        public MemberBuilder withEmail(String email){
            this.eMail = email;
            return this;
        }
        public MemberBuilder withFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }
        public MemberBuilder withLastName(String lastName){
            this.lastName = lastName;
            return this;
        }
        public MemberBuilder withStreetName(String streetName){
            this.streetName = streetName;
            return this;
        }
        public MemberBuilder withHouseNumber(int houseNumber){
            this.houseNumber = houseNumber;
            return this;
        }
        public MemberBuilder withPostalCode(int postalCode){
            this.postalCode = postalCode;
            return this;
        }
        public MemberBuilder withCity(String city){
            this.city = city;
            return this;
        }
        public Member build(){
            return new Member(this);
        }

    }


}

