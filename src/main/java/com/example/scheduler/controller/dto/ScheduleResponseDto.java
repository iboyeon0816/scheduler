package com.example.scheduler.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleResponseDto {
    private Long scheduleId;
    private Long authorId;
    private String authorName;
    private String task;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
