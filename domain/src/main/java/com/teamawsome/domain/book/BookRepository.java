package com.teamawsome.domain.book;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookRepository {
    List<Book> bookList=new ArrayList<>();


    public List<Book> getAllBooks(){
        return bookList;
    }


    public Book addBook(Book book) {
        bookList.add(book);
        return book;

    }
    public Book getBook(String ISBN){
        return bookList.stream()
                .filter(book -> book.getISBN().equals(ISBN))
                .findFirst()
                .orElseThrow(BookNotPresentException::new);
    }
}
