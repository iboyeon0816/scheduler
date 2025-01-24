package com.example.scheduler.repository;

import com.example.scheduler.entity.Schedule;
import com.example.scheduler.service.dto.ScheduleUpdateParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    void save(Schedule schedule);
    List<Schedule> findAll(LocalDate updatedAt, Long authorId, int page, int size);
    Optional<Schedule> findById(Long scheduleId);
    void updateById(Long scheduleId, ScheduleUpdateParam updateParam);
    void deleteById(Long scheduleId);
}
