package com.example.scheduler.repository;

import com.example.scheduler.controller.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;
import com.example.scheduler.service.dto.ScheduleUpdateParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    void save(Schedule schedule);
    void updateById(Long scheduleId, ScheduleUpdateParam updateParam);
    void deleteById(Long scheduleId);
    Optional<String> findPasswordById(Long scheduleId);
    Optional<ScheduleResponseDto> findDtoById(Long scheduleId);
    List<ScheduleResponseDto> findAllDtos(LocalDate updatedAt, Long authorId, int page, int size);
}
