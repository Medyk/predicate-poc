package com.example.demo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface BookJpaRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    // Nie musisz tu pisać żadnych metod!
}
