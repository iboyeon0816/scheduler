package com.example.scheduler.service;

import com.example.scheduler.controller.dto.ScheduleRequestDto.ScheduleCreateDto;
import com.example.scheduler.controller.dto.ScheduleRequestDto.ScheduleDeleteDto;
import com.example.scheduler.controller.dto.ScheduleResponseDto;
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

import static com.example.scheduler.controller.dto.ScheduleRequestDto.ScheduleUpdateDto;

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
        return scheduleRepository.findDtoById(schedule.getId()).orElseThrow(IllegalStateException::new);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(LocalDate updatedAt, Long authorId, int page, int size) {
        return scheduleRepository.findAllDtos(updatedAt, authorId, page, size);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long scheduleId) {
        return scheduleRepository.findDtoById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public ScheduleResponseDto updateScheduleById(Long scheduleId, ScheduleUpdateDto updateDto) {
        String password = scheduleRepository.findPasswordById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        checkPasswordMatch(password, updateDto.getPassword());

        scheduleRepository.updateById(scheduleId, updateDto.getTask());
        return scheduleRepository.findDtoById(scheduleId).orElseThrow(IllegalStateException::new);
    }

    @Override
    @Transactional
    public void deleteScheduleById(Long scheduleId, ScheduleDeleteDto deleteDto) {
        String password = scheduleRepository.findPasswordById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        checkPasswordMatch(password, deleteDto.getPassword());

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
