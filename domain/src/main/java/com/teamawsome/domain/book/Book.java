package com.teamawsome.domain.book;

public class Book {
    private Author author;
    private String ISBN;
    private String title;
    private String summary;


    public Book(Author author, String ISBN, String title, String summary){
        this.author=author;
        this.ISBN=ISBN;
        this.title=title;
        this.summary=summary;
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
}
