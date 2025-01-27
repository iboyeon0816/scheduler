package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleRequestDto.ScheduleCreateDto;
import com.example.scheduler.dto.ScheduleRequestDto.ScheduleDeleteDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.exception.ex.UnAuthenticatedException;
import com.example.scheduler.exception.ex.NotFoundException;
import com.example.scheduler.repository.AuthorRepository;
import com.example.scheduler.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        long scheduleId = scheduleRepository.save(createDto);
        return scheduleRepository.findDtoById(scheduleId).orElseThrow(IllegalStateException::new);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(LocalDate updatedAt, Long authorId, int page, int size) {
        return scheduleRepository.findAllDtos(updatedAt, authorId, page, size);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long scheduleId) {
        return scheduleRepository.findDtoById(scheduleId)
                .orElseThrow(() -> new NotFoundException("Schedule with ID " + scheduleId + " not found"));
    }

    @Override
    @Transactional
    public ScheduleResponseDto updateScheduleById(Long scheduleId, ScheduleUpdateDto updateDto) {
        String password = scheduleRepository.findPasswordById(scheduleId)
                .orElseThrow(() -> new NotFoundException("Schedule with ID " + scheduleId + " not found"));

        checkPasswordMatch(password, updateDto.getPassword());

        scheduleRepository.updateById(scheduleId, updateDto.getTask());
        return scheduleRepository.findDtoById(scheduleId).orElseThrow(IllegalStateException::new);
    }

    @Override
    @Transactional
    public void deleteScheduleById(Long scheduleId, ScheduleDeleteDto deleteDto) {
        String password = scheduleRepository.findPasswordById(scheduleId)
                .orElseThrow(() -> new NotFoundException("Schedule with ID " + scheduleId + " not found"));

        checkPasswordMatch(password, deleteDto.getPassword());

        scheduleRepository.deleteById(scheduleId);
    }

    private void checkAuthorExists(Long authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new NotFoundException("Author with ID " + authorId + " not found");
        }
    }

    private static void checkPasswordMatch(String password, String inputPassword) {
        if (!password.equals(inputPassword)) {
            throw new UnAuthenticatedException("The password for this schedule is incorrect");
        }
    }
}
