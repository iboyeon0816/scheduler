package com.example.scheduler.service;

import com.example.scheduler.controller.dto.ScheduleRequestDto.ScheduleCreateDto;
import com.example.scheduler.controller.dto.ScheduleRequestDto.ScheduleDeleteDto;
import com.example.scheduler.controller.dto.ScheduleRequestDto.ScheduleUpdateDto;
import com.example.scheduler.controller.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto createSchedule(ScheduleCreateDto createDto);
    List<ScheduleResponseDto> findAllSchedules(LocalDate updatedAt, Long authorId, int page, int size);
    ScheduleResponseDto findScheduleById(Long scheduleId);
    ScheduleResponseDto updateScheduleById(Long scheduleId, ScheduleUpdateDto updateDto);
    void deleteScheduleById(Long scheduleId, ScheduleDeleteDto deleteDto);
}
