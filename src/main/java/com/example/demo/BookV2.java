package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"modelVersion"})
public record BookV2(Long id, String title, String author, String extraField) {
    public static final String MODEL_VERSION = "v2";

    @JsonProperty("modelVersion")
    public String getModelVersion() {
        return MODEL_VERSION;
    }

    /**
     * Static factory method to create BookV2 from a Book record.
     */
    public static BookV2 fromBook(Book book) {
        // Map the existing fields and provide defaults or logic for new ones
        return new BookV2(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                "New V2 Data" // Placeholder for whatever new data V2 requires
        );
    }
}