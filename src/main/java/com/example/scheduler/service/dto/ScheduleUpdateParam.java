package com.example.scheduler.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ScheduleUpdateParam {
    private String task;
    private LocalDateTime updatedAt;
}
