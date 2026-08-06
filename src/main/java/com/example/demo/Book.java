package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
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
@JsonPropertyOrder({"model"})
public class Book {
    public static final String MODEL = "1";

    @JsonProperty("model")
    public String getModel() {
        return MODEL;
    }

    @Id
    public Long id;
    public String title;
    public String author;
    public String isbn;
    @Column(name = "pub_year")
    public Integer year;
}
