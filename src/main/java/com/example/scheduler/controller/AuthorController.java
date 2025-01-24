package com.example.scheduler.controller;

import com.example.scheduler.controller.dto.AuthorRequestDto;
import com.example.scheduler.controller.dto.AuthorResponseDto;
import com.example.scheduler.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorResponseDto> createAuthor(@Valid @RequestBody AuthorRequestDto authorRequestDto) {
        AuthorResponseDto resultDto = authorService.createAuthor(authorRequestDto);
        return new ResponseEntity<>(resultDto, HttpStatus.CREATED);
    }
}
