package com.example.scheduler.dto;

import com.example.scheduler.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

public class ScheduleResponseDto {
    @Getter
    public static class ScheduleCreateResultDto {
        private final Long scheduleId;
        private final String authorName;
        private final String task;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;

        public ScheduleCreateResultDto(Schedule schedule) {
            this.scheduleId = schedule.getId();
            this.authorName = schedule.getAuthorName();
            this.task = schedule.getTask();
            this.createdAt = schedule.getCreatedAt();
            this.updatedAt = schedule.getUpdatedAt();
        }
    }
}
