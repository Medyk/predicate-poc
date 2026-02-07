package com.example.demo;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class BookJpaService {

    private final BookJpaRepository bookRepository;

    // Wstrzykiwanie przez konstruktor (zgodnie z Twoimi wytycznymi)
    public BookJpaService(BookJpaRepository bookRepository) {
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

    public List<Book> searchBooks(final BookQuery query, final Pageable pageable) {
        // 1. Zbieramy specyfikacje (podobnie jak wcześniej Predykaty)
        List<Specification<Book>> specs = new ArrayList<>();

        if (query.author != null) {
            specs.add(BookSpecifications.hasAuthorIn(query.author));
        }

        // Zakładamy, że BookSpecifications to klasa, którą stworzyliśmy w poprzednich krokach
//        specs.add(BookSpecifications.hasTitleIn(query.title));
        specs.add(BookSpecifications.hasFieldNameIn("title", query.title, query.search));

        if (query.minYear != null) {
            specs.add((root, q, cb) -> cb.greaterThanOrEqualTo(root.get("year"), query.minYear));
        }

        if (query.maxYear != null) {
            specs.add((root, q, cb) -> cb.lessThanOrEqualTo(root.get("year"), query.maxYear));
        }

        // 2. Redukcja: Łączymy listę w jedną Specyfikację używając AND
        Specification<Book> combinedSpec = specs.stream()
                // Specification.where(null) to element neutralny (jak "true" w poprzednim przykładzie)
                // Dzięki temu, jeśli lista jest pusta, pobieramy wszystko.
                .reduce(Specification.where(BookSpecifications.always()), Specification::and);

        // 3. Wykonanie zapytania SQL na bazie
        return bookRepository.findAll(combinedSpec, pageable).getContent();
    }
}