package com.example.scheduler.entity;

import com.example.scheduler.controller.dto.AuthorRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Author {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Author(AuthorRequestDto requestDto) {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.createdAt = now;
        this.updatedAt = now;
    }
}
