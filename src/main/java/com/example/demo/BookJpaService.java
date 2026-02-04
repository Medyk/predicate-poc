package com.example.demo;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class BookJpaService {

    private final BookJpaRepository bookRepository;

    // Wstrzykiwanie przez konstruktor (zgodnie z Twoimi wytycznymi)
    public BookJpaService(BookJpaRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> searchBooks(final BookQuery query, final int limit) {
        // 1. Zbieramy specyfikacje (podobnie jak wcześniej Predykaty)
        List<Specification<Book>> specs = new ArrayList<>();

        if (query.author != null) {
            specs.add(BookSpecifications.hasAuthorIn(query.author));
        }

        // Zakładamy, że BookSpecifications to klasa, którą stworzyliśmy w poprzednich krokach
        specs.add(BookSpecifications.hasTitleIn(query.title));
        // specs.add(BookSpecifications.hasFieldNameIn(query.title, query.author));

        if (query.minYear != null) {
            specs.add((root, q, cb) -> cb.greaterThanOrEqualTo(root.get("pub_year"), query.minYear));
        }

        if (query.maxYear != null) {
            specs.add((root, q, cb) -> cb.lessThanOrEqualTo(root.get("pub_year"), query.maxYear));
        }

        // 2. Redukcja: Łączymy listę w jedną Specyfikację używając AND
        Specification<Book> combinedSpec = specs.stream()
                // Specification.where(null) to element neutralny (jak "true" w poprzednim przykładzie)
                // Dzięki temu, jeśli lista jest pusta, pobieramy wszystko.
                .reduce(Specification.where(BookSpecifications.always()), Specification::and);

        // 3. Wykonanie zapytania SQL na bazie
        return bookRepository.findAll(combinedSpec, Pageable.ofSize(limit)).getContent();
    }
}