package com.teamawsome.domain.dto;

import java.util.Objects;

public class BookAddedDto {
    String isbn;
    String title;
    String lastName;
    String firstName;
    String summary;

    public BookAddedDto(String isbn, String title, String lastName, String firstName, String summary) {
        this.isbn = isbn;
        this.title = title;
        this.lastName = lastName;
        this.firstName = firstName;
        this.summary = summary;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSummary() {
        return summary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookAddedDto)) return false;
        BookAddedDto that = (BookAddedDto) o;
        return Objects.equals(getIsbn(), that.getIsbn()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getSummary(), that.getSummary());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIsbn(), getTitle(), getLastName(), getFirstName(), getSummary());
    }

    @Override
    public String toString() {
        return "BookAddedDto{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
