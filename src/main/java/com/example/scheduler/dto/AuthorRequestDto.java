package com.example.scheduler.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AuthorRequestDto {
    @NotBlank
    @Size(max = 30)
    private String name;
    @NotBlank
    @Email
    @Size(max = 30)
    private String email;
}
