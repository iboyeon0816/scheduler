package com.example.scheduler.service;

import com.example.scheduler.controller.dto.AuthorRequestDto;
import com.example.scheduler.controller.dto.AuthorResponseDto;

public interface AuthorService {
    AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto);
}
