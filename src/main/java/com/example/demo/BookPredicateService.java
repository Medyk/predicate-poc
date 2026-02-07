package com.example.demo;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


@Service
public class BookPredicateService {
    private final BookPredicateRepository bookRepository;

    public BookPredicateService(BookPredicateRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Book patchBook(final BookPatch patch) {
        if (patch.uid == null) return null;
        Book book = bookRepository.findById(patch.uid).orElse(null);
        if (book == null) return null;
        if (patch.title != null) book.setTitle(patch.title.orElse(null));
        if (patch.author != null) book.setAuthor(patch.author.orElse(null));
        if (patch.year != null) book.setYear(patch.year.orElse(null));
        return bookRepository.save(book);
    }

    public Book getBook(final Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    /**
     *
     * @param query
     * @param pageable
     * @return
     */
    public List<Book> searchBooks(final BookQuery query, final Pageable pageable) {
        // 1. Budowanie listy filtrów (dynamicznie!)
        List<Predicate<Book>> filters = new ArrayList<>();

        // Dodajemy filtry tylko, jeśli są potrzebne (Twoja logika z BookPredicates)
        if (query.author != null) {
            filters.add(BookPredicates.hasAuthorIn(query.author));
        }

        // Predicate supports null or empty lists
        filters.add(BookPredicates.hasTitleIn(query.title));

        if (query.minYear != null) {
            filters.add(book -> book.getYear() >= query.minYear);
        }

        if (query.maxYear != null) {
            filters.add(book -> book.getYear() <= query.maxYear);
        }

        // 2. Wywołanie generycznej metody (to zastępuje repository.findAll(spec))
        return bookRepository.findAll(filters, pageable);
    }
}
