package com.teamawsome.api.book;

import com.teamawsome.domain.dto.BookDto;
import com.teamawsome.domain.book.Author;
import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.service.BookMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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