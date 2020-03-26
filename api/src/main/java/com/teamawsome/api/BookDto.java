package com.teamawsome.api;

import com.teamawsome.domain.book.Author;

import java.util.Objects;

public class BookDto {
        private Author author;
        private String ISBN;
        private String title;
        private String summary;


        public BookDto(Author author, String ISBN, String title, String summary) {
            this.author = author;
            this.ISBN = ISBN;
            this.title = title;
            this.summary = summary;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(author, bookDto.author) &&
                Objects.equals(ISBN, bookDto.ISBN) &&
                Objects.equals(title, bookDto.title) &&
                Objects.equals(summary, bookDto.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, ISBN, title, summary);
    }
}

