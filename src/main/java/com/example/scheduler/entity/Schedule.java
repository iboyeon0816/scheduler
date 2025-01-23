package com.example.scheduler.entity;

import com.example.scheduler.dto.ScheduleRequestDto.ScheduleCreateDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class Schedule {
    @Setter
    private Long id;
    private String authorName;
    private String password;
    private String task;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule(ScheduleCreateDto createDto) {
        LocalDateTime now = LocalDateTime.now();
        this.authorName = createDto.getAuthorName();
        this.password = createDto.getPassword();
        this.task = createDto.getTask();
        this.createdAt = now;
        this.updatedAt = now;
    }
}
