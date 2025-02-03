package com.example.scheduler.repository;

import com.example.scheduler.dto.AuthorRequestDto;
import com.example.scheduler.entity.Author;

import java.util.Optional;

public interface AuthorRepository {
    /**
     * 새로운 작성자 정보를 저장하고, 생성된 작성자의 ID를 반환한다.
     *
     * @param authorRequestDto 저장할 작성자의 정보
     * @return 생성된 작성자의 ID
     */
    long save(AuthorRequestDto authorRequestDto);

    boolean existsById(Long authorId);

    Optional<Author> findById(Long authorId);
}
