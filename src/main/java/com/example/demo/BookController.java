package com.example.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);

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
    public List<Book> getBooks(final BookQuery query) {
        return bookPredicateService.searchBooks(query, 5);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Book getBook(@PathVariable final Long id) {
        return bookPredicateService.getBook(id);
    }

    @PatchMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Book> patchBook(@RequestBody final BookPatch patch) {
        log.info("PATCH: {}", patch);
        Book book = bookPredicateService.patchBook(patch);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book not found");
        }
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .header(HttpHeaders.LOCATION, "/" + book.getId())
                .body(book);
    }

    @GetMapping(value = "/jpa", produces = "application/json")
    public List<Book> getBooksJpa(final BookQuery query) {
        return bookJpaService.searchBooks(query, 5);
    }

    @GetMapping(value = "/jpa/{id}", produces = "application/json")
    public Book getBookJpa(@PathVariable final Long id) {
        return bookJpaService.getBook(id);
    }

    @PatchMapping(value = "/jpa", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Book> patchBookJpa(@RequestBody final BookPatch patch) {
        log.info("PATCH: {}", patch);
        Book book = bookJpaService.patchBook(patch);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book not found");
        }
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .header(HttpHeaders.LOCATION, "/jpa/" + book.getId())
                .body(book);
    }
}
