package com.example.scheduler.repository;

import com.example.scheduler.controller.dto.AuthorResponseDto;
import com.example.scheduler.entity.Author;

import java.util.Optional;

public interface AuthorRepository {
    void save(Author author);
    boolean existsById(Long authorId);
    Optional<AuthorResponseDto> findDtoById(Long authorId);
}
