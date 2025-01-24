package com.example.scheduler.repository;

public interface AuthorRepository {
    boolean existsById(Long authorId);
    String findNameById(Long authorId);
}
