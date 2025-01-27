package com.example.scheduler.dto;

import com.example.scheduler.entity.Author;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuthorResponseDto {
    private final Long authorId;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public AuthorResponseDto(Author author) {
        this.authorId = author.getId();
        this.name = author.getName();
        this.email = author.getEmail();
        this.createdAt = author.getCreatedAt();
        this.updatedAt = author.getUpdatedAt();
    }
}
