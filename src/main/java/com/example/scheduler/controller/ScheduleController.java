package com.example.scheduler.controller;

import com.example.scheduler.dto.ScheduleRequestDto.ScheduleCreateDto;
import com.example.scheduler.dto.ScheduleRequestDto.ScheduleDeleteDto;
import com.example.scheduler.dto.ScheduleRequestDto.ScheduleUpdateDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody ScheduleCreateDto createDto) {
        ScheduleResponseDto resultDto = scheduleService.createSchedule(createDto);
        return new ResponseEntity<>(resultDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(@RequestParam(required = false) LocalDate updatedAt,
                                                                      @RequestParam(required = false) Long authorId) {
        List<ScheduleResponseDto> resultDtos = scheduleService.findAllSchedules(updatedAt, authorId);
        return ResponseEntity.ok(resultDtos);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long scheduleId) {
        ScheduleResponseDto resultDto = scheduleService.findScheduleById(scheduleId);
        return ResponseEntity.ok(resultDto);
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateScheduleById(@PathVariable Long scheduleId,
                                                                  @Valid @RequestBody ScheduleUpdateDto updateDto) {
        ScheduleResponseDto resultDto = scheduleService.updateScheduleById(scheduleId, updateDto);
        return ResponseEntity.ok(resultDto);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteScheduleById(@PathVariable Long scheduleId,
                                                   @Valid @RequestBody ScheduleDeleteDto deleteDto) {
        scheduleService.deleteScheduleById(scheduleId, deleteDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
