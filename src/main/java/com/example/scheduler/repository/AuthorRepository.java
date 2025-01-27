package com.example.scheduler.repository;

import com.example.scheduler.controller.dto.AuthorRequestDto;
import com.example.scheduler.entity.Author;

import java.util.Optional;

public interface AuthorRepository {
    long save(AuthorRequestDto authorRequestDto);
    boolean existsById(Long authorId);
    Optional<Author> findById(Long authorId);
}
