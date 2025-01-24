package com.example.scheduler.entity;

import com.example.scheduler.dto.ScheduleRequestDto.ScheduleCreateDto;
import com.example.scheduler.dto.ScheduleRequestDto.ScheduleUpdateDto;
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
        LocalDateTime now = LocalDateTime.now().withNano(0);
        this.authorId = createDto.getAuthorId();
        this.password = createDto.getPassword();
        this.task = createDto.getTask();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public void update(ScheduleUpdateDto updateDto) {
//        this.authorName = updateDto.getAuthorName();
        this.task = updateDto.getTask();
        this.updatedAt = LocalDateTime.now().withNano(0);
    }
}
