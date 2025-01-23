package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleRequestDto.ScheduleCreateDto;
import com.example.scheduler.dto.ScheduleRequestDto.ScheduleUpdateDto;
import com.example.scheduler.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto createSchedule(ScheduleCreateDto createDto);
    List<ScheduleResponseDto> findAllSchedules(LocalDate updatedAt, String authorName);
    ScheduleResponseDto findScheduleById(Long scheduleId);
    ScheduleResponseDto updateScheduleById(Long scheduleId, ScheduleUpdateDto updateDto);
}
