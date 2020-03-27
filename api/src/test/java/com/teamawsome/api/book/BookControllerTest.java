package com.teamawsome.api.book;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamawsome.domain.book.Author;
import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.book.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@ComponentScan
@WebMvcTest(BookController.class)
class BookControllerTest {
    @MockBean
    BookRepository bookRepository;
    @Autowired
    MockMvc mockMvc;

    private String objectToJSON(Object object) {
        try{
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e){
            throw new AssertionError("Mapping object to JSON failed", e);
        }
    }

    @Test
    void getAllBooks() throws Exception {
        Author dries = new Author("dries", "bodaer");
        List<Book> returnValue = List.of(new Book(dries, "1354", "booktitle", "summary"),
                                new Book(dries, "1234567890", "An aweful book", "Very aweful book"));

        String expected = objectToJSON(
                                List.of(new BookDto(dries, "1354", "booktitle", "summary"),
                                new BookDto(dries, "1234567890", "An aweful book", "Very aweful book"))
                            );

        Mockito.when(bookRepository.getAllBooks()).thenReturn(returnValue);

        String actual = mockMvc.perform(get("/books")
                    .with(user("user")
                        .password("password")
                        .roles("Admin")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT);


    }

    @Test
    public void findBookByISBN() throws Exception{
        List<Book> returnedBooks = List.of(
                new Book(
                    new Author("Igor","Zhirkov"),
                    "9781484224021",
                    "Low-level Programming",
                    "Low-level Programming explains Intel 64 architecture as the result of Von Neumann architecture evolution" ),
                 new Book(
                    new Author("Joshua", "Bloch"),
                    "9780134685991",
                    "Effective Java",
                    "The definitive guide to Java Platform best practices" )
        );
        String expected = objectToJSON(List.of(
                new BookDto(
                        new Author("Igor","Zhirkov"),
                        "9781484224021",
                        "Low-level Programming",
                        "Low-level Programming explains Intel 64 architecture as the result of Von Neumann architecture evolution" ),
                new BookDto(
                        new Author("Joshua", "Bloch"),
                        "9780134685991",
                        "Effective Java",
                        "The definitive guide to Java Platform best practices" )
        ));

        Mockito.when(bookRepository.findByISBNWildCard("013*82")).thenReturn(returnedBooks);
        String actual = mockMvc.perform(get("/books/isbn")
                                        .queryParam("wildcard","013*82")
                                        .with(user("user")
                                                .password("password")
                                                .roles("Admin")) )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT);
    }
}