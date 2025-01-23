package com.example.scheduler.repository;

import com.example.scheduler.entity.Schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    void save(Schedule schedule);
    List<Schedule> findAll(LocalDate updatedAt, String authorName);
    Optional<Schedule> findById(Long scheduleId);
    void updateById(Long scheduleId, Schedule schedule);
    void deleteById(Long scheduleId);
}
