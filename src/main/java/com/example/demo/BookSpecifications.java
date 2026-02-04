package com.example.demo;


import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public class BookSpecifications {
    public static Specification<Book> always() {
        return (root, query, cb) -> null; // or cb.conjunction();
    }

    public static Specification<Book> hasAuthorIn(List<String> values) {
        return hasFieldNameIn("author", values);
    }

    public static Specification<Book> hasTitleIn(List<String> values) {
        return hasFieldNameIn("title", values);
    }

    public static Specification<Book> hasFieldNameIn(String fieldName, List<String> values) {
        return (root, query, cb) -> {
            // 1. Jeśli lista jest null lub pusta -> nie filtruj (zwróć null)
            if (values == null || values.isEmpty()) {
                return null; // or cb.conjunction() - WHERE 1=1
            }

            // 2. Jeśli lista ma dokładnie 1 element -> użyj equal
            if (values.size() == 1) {
                return cb.equal(root.get(fieldName), values.getFirst());
            }

            // 3. Jeśli lista ma 2 lub więcej elementów -> użyj IN
            // Metoda .in() na obiekcie Path (root.get) przyjmuje kolekcję
            return root.get(fieldName).in(values);
        };
    }
}
