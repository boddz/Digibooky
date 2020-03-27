package com.teamawsome.api.rental;

public class RentBookDto {

    private String isbn;
    private String memberId;

    public RentBookDto(String isbn, String memberId) {
        this.isbn = isbn;
        this.memberId = memberId;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getMemberId() {
        return memberId;
    }
}
