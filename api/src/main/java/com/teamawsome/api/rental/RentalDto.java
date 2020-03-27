package com.teamawsome.api.rental;

public class RentalDto {

    private int lendingNumber;
    private int memberId;
    private String isbn;

    public RentalDto(int lendingNumber, int memberId, String isbn) {
        this.lendingNumber = lendingNumber;
        this.memberId = memberId;
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }
}
