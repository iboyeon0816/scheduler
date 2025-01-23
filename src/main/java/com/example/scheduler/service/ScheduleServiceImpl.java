package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleRequestDto.ScheduleCreateDto;
import com.example.scheduler.dto.ScheduleResponseDto.ScheduleCreateResultDto;
import com.example.scheduler.entity.Schedule;
import com.example.scheduler.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    @Override
    public ScheduleCreateResultDto createSchedule(ScheduleCreateDto createDto) {
        Schedule schedule = new Schedule(createDto);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleCreateResultDto(savedSchedule);
    }
}
