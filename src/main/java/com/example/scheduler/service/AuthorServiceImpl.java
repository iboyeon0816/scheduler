package com.example.scheduler.service;

import com.example.scheduler.dto.AuthorRequestDto;
import com.example.scheduler.dto.AuthorResponseDto;
import com.example.scheduler.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto) {
        long authorId = authorRepository.save(authorRequestDto);
        return authorRepository.findById(authorId)
                .map(AuthorResponseDto::new)
                .orElseThrow(IllegalStateException::new);
    }
}
