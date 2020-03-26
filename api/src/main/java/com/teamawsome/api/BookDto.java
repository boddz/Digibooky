package com.teamawsome.api;

import com.teamawsome.domain.book.Author;

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
    }

