package com.example.scheduler.controller;

import com.example.scheduler.dto.ScheduleRequestDto.ScheduleCreateDto;
import com.example.scheduler.dto.ScheduleRequestDto.ScheduleDeleteDto;
import com.example.scheduler.dto.ScheduleRequestDto.ScheduleUpdateDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.service.ScheduleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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

    private static final int DEFAULT_SIZE = 4; // 기본 페이지 크기

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody ScheduleCreateDto createDto) {
        ScheduleResponseDto resultDto = scheduleService.createSchedule(createDto);
        return new ResponseEntity<>(resultDto, HttpStatus.CREATED);
    }

    /**
     * 일정 목록을 조회하는 API
     *
     * `updatedAt`과 `authorId`로 필터링 기능 제공
     * `page`와 `size`로 페이징 기능 제공
     *
     * @param updatedAt 해당 날짜에 수정된 일정만 조회 (선택적)
     * @param authorId 해당 작성자의 일정만 조회 (선택적)
     * @param page 조회할 페이지 번호 (기본값: 1)
     * @param size 페이지 당 일정 수 (기본값: DEFAULT_SIZE)
     * @return 일정 목록 응답
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(@RequestParam(required = false) LocalDate updatedAt,
                                                                      @RequestParam(required = false) Long authorId,
                                                                      @RequestParam(defaultValue = "1") @Min(1) int page,
                                                                      @RequestParam(defaultValue = "" + DEFAULT_SIZE) @Min(1) int size) {
        List<ScheduleResponseDto> resultDtos = scheduleService.findAllSchedules(updatedAt, authorId, page, size);
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
