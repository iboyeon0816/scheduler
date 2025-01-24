package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleRequestDto.ScheduleCreateDto;
import com.example.scheduler.dto.ScheduleRequestDto.ScheduleDeleteDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;
import com.example.scheduler.repository.AuthorRepository;
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
    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleCreateDto createDto) {
        checkAuthorExists(createDto.getAuthorId());
        Schedule schedule = new Schedule(createDto);
        scheduleRepository.save(schedule);
        return new ScheduleResponseDto(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(LocalDate updatedAt, Long authorId) {
        return scheduleRepository.findAll(updatedAt, authorId).stream()
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

        checkPasswordMatch(schedule.getPassword(), updateDto.getPassword());

        schedule.update(updateDto);
        scheduleRepository.updateById(scheduleId, schedule);
        return new ScheduleResponseDto(schedule);
    }

    @Override
    @Transactional
    public void deleteScheduleById(Long scheduleId, ScheduleDeleteDto deleteDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        checkPasswordMatch(schedule.getPassword(), deleteDto.getPassword());

        scheduleRepository.deleteById(scheduleId);
    }

    private void checkAuthorExists(Long authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private static void checkPasswordMatch(String password, String inputPassword) {
        if (!password.equals(inputPassword)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
