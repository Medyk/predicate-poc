package com.example.demo;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;


@Component
public final class BookPredicateRepository {
    private static final AtomicLong serial = new AtomicLong(21L);

    public Optional<Book> findById(Long id) {
        return allBooksStorage.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    public Book save(Book book) {
        findById(book.getId()).ifPresentOrElse(
                existingBook -> {
                    int index = allBooksStorage.indexOf(existingBook);
                    allBooksStorage.set(index, book);
                },
                () -> {
                    book.setId(serial.incrementAndGet());
                    allBooksStorage.add(book);
                }
        );
        return book;
    }


    /**
     * Generyczna metoda filtrująca listę obiektów na podstawie listy predykatów.
     * Działa jak logiczne AND dla wszystkich warunków.
     *
     * @param predicates Lista warunków (np. maAutora, jestDostępna)
     * @param pageable Limit wyników
     * @return Przefiltrowana lista
     */
    public List<Book> findAll(List<Predicate<Book>> predicates, final Pageable pageable) {
        // 1. Redukcja listy warunków do jednego "Super-Predykatu"
        // x -> true to element neutralny (jak 1 w mnożeniu),
        // dzięki temu pusta lista filtrów zwróci wszystko.
        Predicate<Book> compositePredicate = predicates.stream()
                .reduce(BookPredicates.always(), Predicate::and);

        // 2. Wykonanie filtrowania
        return allBooksStorage.stream()
                .filter(compositePredicate)
                // does not support sorting
                .limit(pageable.getPageSize())
                .toList(); // Wymaga Java 16+
    }


    /**
     * Local storage.
     */
    private static final List<Book> allBooksStorage = new ArrayList<>(List.of(
            new Book(1L, "Effective Java", "Joshua Bloch", "978-0134685991", 2018),
            new Book(2L, "Clean Code", "Robert C. Martin", "978-0132350884", 2008),
            new Book(3L, "Effective Java (2nd Edition)", "Joshua Bloch", "978-0321356680", 2008),
            new Book(4L, "Core Java Volume I", "Cay S. Horstmann", "978-0134494166", 2018),
            new Book(5L, "Head First Java", "Kathy Sierra", "978-0596009205", 2005),
            new Book(6L, "Effective Python", "Brett Slatkin", "978-1491901946", 2015),
            new Book(7L, "Design Patterns", "Erich Gamma", "978-0201633610", 1994),
            new Book(8L, "Thinking in Java", "Bruce Eckel", "978-0131872486", 2006),
            new Book(9L, "Core Java Volume II", "Cay S. Horstmann", "978-0132575669", 2012),
            new Book(10L, "Building Microservices", "Sam Newman", "978-1449344856", 2015),
            new Book(11L, "Domain-Driven Design", "Eric Evans", "978-0321125217", 2003),
            new Book(12L, "Refactoring", "Martin Fowler", "978-0134757599", 2018),
            new Book(13L, "Spring in Action", "Craig Walls", "978-1617294549", 2018),
            new Book(14L, "Spring Boot in Action", "Craig Walls", "978-1617292545", 2016),
            new Book(15L, "Designing Data-Intensive Applications", "Martin Kleppmann", "978-1491950357", 2017),
            new Book(16L, "Test Driven Development", "Kent Beck", "978-0321712905", 2002),
            new Book(17L, "Core Java SE 9", "Cay S. Horstmann", "978-0134494166", 2016),
            new Book(18L, "Clean Code (Reprint)", "Robert C. Martin", "978-0132350884", 2009),
            new Book(19L, "The Clean Coder", "Robert C. Martin", "978-0137081073", 2011),
            new Book(20L, "Patterns of Enterprise Application Architecture", "Martin Fowler", "978-0321127426", 2002)
    ));
}
