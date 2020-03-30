package com.teamawsome.domain.book;

import java.util.Objects;

public class FindByAuthorDto {
    private String firstName;
    private String lastName;

    private FindByAuthorDto(){}
    public FindByAuthorDto(String firstName, String lastName){
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FindByAuthorDto that = (FindByAuthorDto) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
