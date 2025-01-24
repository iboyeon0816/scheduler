package com.example.scheduler.repository;

import com.example.scheduler.entity.Author;

public interface AuthorRepository {
    void save(Author author);
    boolean existsById(Long authorId);
}
