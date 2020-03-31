package com.teamawsome.domain.dto;

public class RentBookDto {
    private String isbn;
    private int memberId;

    public RentBookDto(String isbn, int memberId) {
        this.isbn = isbn;
        this.memberId = memberId;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getMemberId() {
        return memberId;
    }
}
