package com.example.scheduler.repository;

import com.example.scheduler.dto.ScheduleRequestDto.ScheduleCreateDto;
import com.example.scheduler.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    /**
     * 새로운 일정을 저장하고, 생성된 일정의 ID를 반환한다.
     *
     * @param createDto 저장할 일정의 정보
     * @return 생성된 일정의 ID
     */
    long save(ScheduleCreateDto createDto);

    void updateById(Long scheduleId, String task);

    void deleteById(Long scheduleId);

    Optional<String> findPasswordById(Long scheduleId);

    Optional<ScheduleResponseDto> findDtoById(Long scheduleId);

    /**
     * 일정 목록을 조회한다.
     *
     * `updatedAt`과 `authorId`로 필터링 기능 제공
     * `page`와 `size`로 페이징 기능 제공
     *
     * @param updatedAt 해당 날짜에 수정된 일정만 조회 (선택적)
     * @param authorId 해당 작성자의 일정만 조회 (선택적)
     * @param page 조회할 페이지 번호
     * @param size 페이지 당 일정 수
     * @return 일정 응답 DTO 리스트
     */
    List<ScheduleResponseDto> findAllDtos(LocalDate updatedAt, Long authorId, int page, int size);
}
