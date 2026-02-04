package com.example.demo;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


@Service
public class BookPredicateService {
    private final BookPredicateRepository bookRepository;

    public BookPredicateService(BookPredicateRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     *
     * @param query
     * @param limit
     * @return
     */
    public List<Book> searchBooks(final BookQuery query, final int limit) {
        // 1. Budowanie listy filtrów (dynamicznie!)
        List<Predicate<Book>> filters = new ArrayList<>();

        // Dodajemy filtry tylko, jeśli są potrzebne (Twoja logika z BookPredicates)
        if (query.author != null) {
            filters.add(BookPredicates.hasAuthorIn(query.author));
        }

        // Predicate supports null or empty lists
        filters.add(BookPredicates.hasTitleIn(query.title));

        if (query.minYear != null) {
            filters.add(book -> book.getPub_year() >= query.minYear);
        }

        if (query.maxYear != null) {
            filters.add(book -> book.getPub_year() <= query.maxYear);
        }

        // 2. Wywołanie generycznej metody (to zastępuje repository.findAll(spec))
        return bookRepository.findAll(filters, limit);
    }
}
