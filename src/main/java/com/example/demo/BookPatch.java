package com.example.demo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.Optional;


@Data
public class BookPatch {
    @JsonProperty("id")
    Long uid;
    @Size(max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9 '\",.]*$")
    Optional<String> title; // support for tri-state
    @Size(max = 100)
    Optional<String> author; // support for tri-state
    @Min(1800)
    @Max(2200)
    Optional<Integer> year; // support for tri-state
}
