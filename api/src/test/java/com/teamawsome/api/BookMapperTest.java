package com.teamawsome.api;

import com.teamawsome.domain.book.Author;
import com.teamawsome.domain.book.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    @Test
    void transformBookToBookDto() {
        Author dries = new Author("dries", "bodaer");
        Book book1 = new Book(dries, "1354", "booktitle", "summary");
        BookDto book1Dto = new BookDto(book1.getAuthor(), book1.getISBN(), book1.getTitle(), book1.getSummary());
        BookMapper bookMapper = new BookMapper();

        Assertions.assertThat(bookMapper.transformBookToBookDto(book1)).isEqualTo(book1Dto);
    }
}