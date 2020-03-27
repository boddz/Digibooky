package com.teamawsome.api.rental;

public class RentBookDto {

    private String isbn;

    public RentBookDto(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }
}
