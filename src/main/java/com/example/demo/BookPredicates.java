package com.example.demo;


import java.util.List;
import java.util.function.Predicate;


public class BookPredicates {
    public static Predicate<Book> always() {
        return book -> true;
    }

    public static Predicate<Book> hasTitleIn(List<String> values) {
        return book -> {
            // 1. Jeśli null lub pusta -> zwróć true (nie filtruj)
            // W Streamach 'true' oznacza "przepuść dalej", w JPA 'null' oznacza "nie dodawaj WHERE"
            if (values == null || values.isEmpty()) {
                return true;
            }

            final String bookTitle = book.getTitle();

            // 2. Jeśli 1 element -> equal
            if (values.size() == 1) {
                return values.getFirst().equals(bookTitle);
            }

            // 3. Jeśli więcej -> IN (contains)
            return values.contains(bookTitle);
        };
    }

    public static Predicate<Book> hasAuthorIn(List<String> values) {
        return book -> {
            // 1. Jeśli null lub pusta -> zwróć true (nie filtruj)
            // W Streamach 'true' oznacza "przepuść dalej", w JPA 'null' oznacza "nie dodawaj WHERE"
            if (values == null || values.isEmpty()) {
                return true;
            }

            final String bookAuthor = book.getAuthor();

            // 2. Jeśli 1 element -> equal
            if (values.size() == 1) {
                return values.getFirst().equals(bookAuthor);
            }

            // 3. Jeśli więcej -> IN (contains)
            return values.contains(bookAuthor);
        };
    }

    public static Predicate<Book> hasIsbnIn(List<String> values) {
        return book -> {
            // 1. Jeśli null lub pusta -> zwróć true (nie filtruj)
            // W Streamach 'true' oznacza "przepuść dalej", w JPA 'null' oznacza "nie dodawaj WHERE"
            if (values == null || values.isEmpty()) {
                return true;
            }

            final String bookIsbn = book.getIsbn();

            // 2. Jeśli 1 element -> equal
            if (values.size() == 1) {
                return values.getFirst().equals(bookIsbn);
            }

            // 3. Jeśli więcej -> IN (contains)
            return values.contains(bookIsbn);
        };
    }
}
