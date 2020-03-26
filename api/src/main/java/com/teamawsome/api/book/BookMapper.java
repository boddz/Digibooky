package com.teamawsome.api.book;


import com.teamawsome.domain.book.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDto transformBookToBookDto(Book book){
        return new BookDto(book.getAuthor(), book.getISBN(), book.getTitle(), book.getSummary());
    }

}
