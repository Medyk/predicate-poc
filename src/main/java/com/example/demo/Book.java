package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "books")
public class Book {
    @Id
    public Long id;
    public String title;
    public String author;
    public String isbn;
    public Integer pub_year;
}
