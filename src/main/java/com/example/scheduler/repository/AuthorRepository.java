package com.example.scheduler.repository;

import com.example.scheduler.entity.Author;

public interface AuthorRepository {
    boolean existsById(Long authorId);
    String findNameById(Long authorId);
    void save(Author author);
}
