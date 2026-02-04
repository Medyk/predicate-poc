package com.example.demo;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class BookController {
    private final BookPredicateService bookPredicateService;
    private final BookJpaService bookJpaService;

    public BookController(BookPredicateService bookPredicateService, BookJpaService bookJpaService) {
        this.bookPredicateService = bookPredicateService;
        this.bookJpaService = bookJpaService;
    }

    /**
     * Get books.
     * 
     * @param query
     * @return
     */
    @GetMapping(value = "/", produces = "application/json")
    public List<Book> getBooks(BookQuery query) {
        return bookPredicateService.searchBooks(query, 5);
    }

    @GetMapping(value = "/jpa", produces = "application/json")
    public List<Book> getBooksJpa(BookQuery query) {
        return bookJpaService.searchBooks(query, 5);
    }
}
