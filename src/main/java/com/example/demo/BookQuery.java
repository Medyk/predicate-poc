package com.example.demo;


import lombok.Data;

import java.util.List;


@Data
public class BookQuery {
    public List<String> isbn;
    public List<String> author;
    public List<String> title;
    public Integer minYear;
    public Integer maxYear;
    public BookSpecifications.Mode search;
}
