package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleRequestDto.ScheduleCreateDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;
import com.example.scheduler.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static com.example.scheduler.dto.ScheduleRequestDto.ScheduleUpdateDto;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    @Override
    public ScheduleResponseDto createSchedule(ScheduleCreateDto createDto) {
        Schedule schedule = new Schedule(createDto);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(LocalDate updatedAt, String authorName) {
        return scheduleRepository.findAll(updatedAt, authorName).stream()
                .map(ScheduleResponseDto::new)
                .toList();
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .map(ScheduleResponseDto::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public ScheduleResponseDto updateScheduleById(Long scheduleId, ScheduleUpdateDto updateDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!schedule.getPassword().equals(updateDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        schedule.update(updateDto);
        scheduleRepository.updateById(scheduleId, schedule);
        return new ScheduleResponseDto(schedule);
    }
}
