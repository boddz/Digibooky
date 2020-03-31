package com.teamawsome.domain.service;

import com.teamawsome.domain.book.Author;
import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.dto.BookAddedDto;
import com.teamawsome.domain.dto.BookDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class BookMapperTest {
    private final BookMapper bookMapper = new BookMapper();

    @Test
    public void toBookDto(){
        final Book book = new Book(
                new Author("Uresh", "Vahalia"),
                "0131019082",
                "UNIX Internals - The new frontiers",
                "This book examines recent advances in modern UNIX systems."
        );

        BookDto expected = new BookDto(book.getAuthor(),book.getISBN(),book.getTitle(),book.getSummary());

        Assertions.assertThat(bookMapper.toBookDto(book)).isEqualTo(expected);
    }
    @Test
    public void bookDtoToBook(){
        final Book expected = new Book(
                new Author("Uresh", "Vahalia"),
                "0131019082",
                "UNIX Internals - The new frontiers",
                "This book examines recent advances in modern UNIX systems."
        );

        BookDto dto = new BookDto(expected.getAuthor(),expected.getISBN(),expected.getTitle(),expected.getSummary());

        Assertions.assertThat(bookMapper.toBook(dto)).isEqualTo(expected);
    }

    @Test
    public void bookAddedDtoToBook(){
        final Book expected = new Book(
                new Author("Uresh", "Vahalia"),
                "0131019082",
                "UNIX Internals - The new frontiers",
                "This book examines recent advances in modern UNIX systems."
        );
        BookAddedDto bookAddedDto = new BookAddedDto(expected.getISBN(),expected.getTitle(), expected.getAuthor().getLastName(),
                expected.getAuthor().getFirstName(), expected.getSummary());

        Assertions.assertThat(bookMapper.toBook(bookAddedDto)).isEqualTo(expected);
    }
}
