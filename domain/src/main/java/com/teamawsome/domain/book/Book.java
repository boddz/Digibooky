package com.teamawsome.domain.book;

import java.util.Objects;

public class Book {
    private Author author;
    private String ISBN;
    private String title;
    private String summary;
    private boolean destroyed;


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
    public boolean isDestroyed(){
        return destroyed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(getAuthor(), book.getAuthor()) &&
                Objects.equals(getISBN(), book.getISBN()) &&
                Objects.equals(getTitle(), book.getTitle()) &&
                Objects.equals(getSummary(), book.getSummary());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthor(), getISBN(), getTitle(), getSummary());
    }

    public void toModify(String lastName, String firstName) {

    }

    public void setAuthor(String lastName, String firstName) {
        Author toChange= new Author(firstName, lastName);
        this.author = toChange;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setDestroyed(boolean bookIsDestroyed){
        destroyed = bookIsDestroyed;
    }
}
