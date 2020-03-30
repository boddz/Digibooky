package com.teamawsome.api.book;

import com.teamawsome.domain.book.Author;

import java.util.Objects;

public class BookDto {
    private Author author;
    private String ISBN;
    private String title;
    private String summary;


    public BookDto(Author author, String ISBN, String title, String summary) {
        this.author = author;
        this.ISBN = checkIfNotEmpty(ISBN);
        this.title = checkIfNotEmpty(title);
        this.summary = summary;
    }

    private String checkIfNotEmpty(String isbn) {
        if (!(isbn == "")) {
            return isbn;
        }
        throw new IllegalArgumentException("Put in something, you moron!");
    }


    public Author getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
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

