package com.example.scheduler.repository;

import com.example.scheduler.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository {
    Schedule save(Schedule schedule);

    List<Schedule> findAll(LocalDate updatedAt, String authorName);
}
