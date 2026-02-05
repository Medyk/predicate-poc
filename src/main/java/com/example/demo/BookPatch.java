package com.example.demo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Optional;


@Data
public class BookPatch {
    @JsonProperty("id")
    Long uid;
    Optional<String> title; // support for tri-state
    Optional<String> author; // support for tri-state
    Optional<Integer> year; // support for tri-state
}
