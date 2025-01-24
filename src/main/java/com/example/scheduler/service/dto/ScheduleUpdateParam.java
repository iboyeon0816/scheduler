package com.example.scheduler.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleUpdateParam {
    private String task;
    private LocalDateTime updatedAt;

    public ScheduleUpdateParam(String task) {
        this.task = task;
        this.updatedAt = LocalDateTime.now().withNano(0);
    }
}
