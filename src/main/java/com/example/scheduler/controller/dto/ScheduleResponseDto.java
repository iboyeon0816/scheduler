package com.example.scheduler.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ScheduleResponseDto {
    private Long scheduleId;
    private Long authorId;
    private String authorName;
    private String task;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
