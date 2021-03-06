package com.teamawsome.domain.book;

import java.util.Objects;

public class Author {
    private String firstName;
    private String lastName;

    private Author(){}
    public Author(String firstName, String lastName){
        assertNotEmpty(lastName);

        this.firstName = firstName;
        this.lastName = lastName;
    }

    private void assertNotEmpty(String text) {
        if (text.isBlank()) {
            throw new IllegalArgumentException("Put in something, you moron!");
        }
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(firstName, author.firstName) &&
                Objects.equals(lastName, author.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

}
