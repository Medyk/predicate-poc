package com.example.demo;


import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public class BookSpecifications {
    public enum Mode {
        normal(false, false),
        icase(true, false),
        like(false, true),
        ilike(true, true);

        Mode(boolean caseInsensitive, boolean like) {
            this.isCaseInsensitive = caseInsensitive;
            this.isLike = like;
        }

        public final boolean isCaseInsensitive;
        public final boolean isLike;
    }

    public static Specification<Book> always() {
        return (root, query, cb) -> null; // or cb.conjunction();
    }

    public static Specification<Book> hasAuthorIn(List<String> values) {
        return hasFieldNameIn("author", values, Mode.normal);
    }

    public static Specification<Book> hasTitleIn(List<String> values) {
        return hasFieldNameIn("title", values, Mode.normal);
    }

    public static Specification<Book> hasFieldNameIn(String fieldName, List<String> values) {
        return hasFieldNameIn(fieldName, values, Mode.normal);
    }

    public static Specification<Book> hasFieldNameIn(final String fieldName, final List<String> inValues, Mode inMode) {
        return (root, query, cb) -> {
            // 1. Jeśli lista jest null lub pusta -> nie filtruj (zwróć null)
            if (fieldName == null || inValues == null || inValues.isEmpty()) {
                return null; // or cb.conjunction() - WHERE 1=1
            }

            // Napraw NULL
            final Mode mode = inMode == null ? Mode.normal : inMode;

            // Zmień na LOWERCASE jeśli taki tryb jest ustawiony
            final Expression<String> fieldValue = mode.isCaseInsensitive ? cb.lower(root.get(fieldName)) : root.get(fieldName);
            final List<String> values = mode.isCaseInsensitive ? inValues.stream().map(String::toLowerCase).toList() : inValues;

            // 2. Ścieżka dla LIKE
            if (mode.isLike) {
                // Standard JPA dla ILIKE: lower(column) LIKE lower(value)
                // value powinien zawierać znaki '%' i/lub '_' jeśli szukasz fragmentu.
                return cb.or(values.stream().map(value -> cb.like(fieldValue, value)).toArray(Predicate[]::new));
            }

            // 3. Jeśli lista ma dokładnie 1 element -> użyj equal
            if (values.size() == 1) {
                return cb.equal(fieldValue, values.getFirst());
            }

            // 4. Jeśli lista ma 2 lub więcej elementów -> użyj IN
            // Metoda .in() na obiekcie Path (root.get) przyjmuje kolekcję
            return fieldValue.in(values);
        };
    }
}
