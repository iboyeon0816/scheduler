package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleRequestDto.ScheduleCreateDto;

import static com.example.scheduler.dto.ScheduleResponseDto.ScheduleCreateResultDto;

public interface ScheduleService {
    ScheduleCreateResultDto createSchedule(ScheduleCreateDto createDto);
}
