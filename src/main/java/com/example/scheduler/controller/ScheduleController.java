package com.example.scheduler.controller;

import com.example.scheduler.dto.ScheduleRequestDto.ScheduleCreateDto;
import com.example.scheduler.dto.ScheduleResponseDto.ScheduleCreateResultDto;
import com.example.scheduler.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleCreateResultDto> createSchedule(@Valid @RequestBody ScheduleCreateDto createDto) {
        ScheduleCreateResultDto resultDto = scheduleService.createSchedule(createDto);
        return new ResponseEntity<>(resultDto, HttpStatus.CREATED);
    }
}
