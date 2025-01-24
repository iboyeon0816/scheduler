package com.example.scheduler.controller.dto;

import com.example.scheduler.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private final Long scheduleId;
    private final Long authorId;
    private final String authorName;
    private final String task;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ScheduleResponseDto(Schedule schedule, String authorName) {
        this.scheduleId = schedule.getId();
        this.authorId = schedule.getAuthorId();
        this.authorName = authorName;
        this.task = schedule.getTask();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }
}
