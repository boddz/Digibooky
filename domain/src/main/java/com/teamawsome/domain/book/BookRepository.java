package com.teamawsome.domain.book;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class BookRepository {
    List<Book> bookList=new ArrayList<>();

    public List<Book> getAllBooks(){
        return bookList;
    }


    public void addBook(Book book) {
        bookList.add(book);

    }
}
