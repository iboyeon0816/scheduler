package com.example.scheduler.repository;

import com.example.scheduler.dto.ScheduleRequestDto.ScheduleCreateDto;
import com.example.scheduler.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    long save(ScheduleCreateDto createDto);
    void updateById(Long scheduleId, String task);
    void deleteById(Long scheduleId);
    Optional<String> findPasswordById(Long scheduleId);
    Optional<ScheduleResponseDto> findDtoById(Long scheduleId);
    List<ScheduleResponseDto> findAllDtos(LocalDate updatedAt, Long authorId, int page, int size);
}
