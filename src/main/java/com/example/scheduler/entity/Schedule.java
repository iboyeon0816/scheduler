package com.example.scheduler.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Schedule {
    private Long id;
    private Long authorId;
    private String password;
    private String task;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
