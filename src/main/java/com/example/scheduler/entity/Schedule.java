package com.example.scheduler.entity;

import com.example.scheduler.controller.dto.ScheduleRequestDto.ScheduleCreateDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {
    private Long id;
    private Long authorId;
    private String password;
    private String task;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule(ScheduleCreateDto createDto) {
        this.authorId = createDto.getAuthorId();
        this.password = createDto.getPassword();
        this.task = createDto.getTask();
    }
}
