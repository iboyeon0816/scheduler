package com.example.scheduler.service;

import com.example.scheduler.controller.dto.AuthorRequestDto;
import com.example.scheduler.controller.dto.AuthorResponseDto;
import com.example.scheduler.entity.Author;
import com.example.scheduler.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;

    @Override
    public AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto) {
        Author author = new Author(authorRequestDto);
        authorRepository.save(author);
        return authorRepository.findDtoById(author.getId()).orElseThrow(IllegalStateException::new);
    }
}
