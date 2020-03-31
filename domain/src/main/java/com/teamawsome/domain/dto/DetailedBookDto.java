package com.teamawsome.domain.dto;

import com.teamawsome.domain.book.Author;
import com.teamawsome.domain.member.Member;

import java.util.Objects;

public class DetailedBookDto extends BookDto {
    private boolean isRented;
    private Member rentedByWho;
    public DetailedBookDto(Author author, String ISBN, String title, String summary, boolean isRented, Member rentedByWho) {
        super(author, ISBN, title, summary);
        this.isRented = isRented;
        this.rentedByWho = rentedByWho;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DetailedBookDto that = (DetailedBookDto) o;
        return isRented == that.isRented &&
                Objects.equals(rentedByWho, that.rentedByWho);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isRented, rentedByWho);
    }
}
