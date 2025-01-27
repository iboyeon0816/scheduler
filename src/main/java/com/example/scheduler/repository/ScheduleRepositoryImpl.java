package com.example.scheduler.repository;

import com.example.scheduler.controller.dto.ScheduleRequestDto.ScheduleCreateDto;
import com.example.scheduler.controller.dto.ScheduleResponseDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public long save(ScheduleCreateDto createDto) {
        String sql = "INSERT INTO schedule (author_id, password, task) VALUES (:authorId, :password, :task)";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(createDto);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void updateById(Long scheduleId, String task) {
        String sql = "UPDATE schedule SET task = :task WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("task", task)
                .addValue("id", scheduleId);

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteById(Long scheduleId) {
        String sql = "DELETE FROM schedule WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", scheduleId);

        jdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<String> findPasswordById(Long scheduleId) {
        String sql = "SELECT password FROM schedule WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", scheduleId);

        try {
            String password = jdbcTemplate.queryForObject(sql, params, String.class);
            return Optional.of(password);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ScheduleResponseDto> findDtoById(Long scheduleId) {
        String sql = "SELECT s.id AS schedule_id, " +
                "s.author_id, " +
                "a.name AS author_name, " +
                "s.task, " +
                "s.created_at, " +
                "s.updated_at " +
                "FROM schedule s " +
                "JOIN author a ON s.author_id = a.id " +
                "WHERE s.id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", scheduleId);

        try {
            ScheduleResponseDto scheduleDto = jdbcTemplate.queryForObject(sql, params, scheduleDtoRowMapper());
            return Optional.of(scheduleDto);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<ScheduleResponseDto> findAllDtos(LocalDate updatedAt, Long authorId, int page, int size) {
        String sql = createSelectQueryFilteringBy(updatedAt, authorId);

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("updatedAt", updatedAt)
                .addValue("authorId", authorId)
                .addValue("limit", size)
                .addValue("offset", (page - 1) * size);

        return jdbcTemplate.query(sql, params, scheduleDtoRowMapper());
    }

    private RowMapper<ScheduleResponseDto> scheduleDtoRowMapper() {
        return BeanPropertyRowMapper.newInstance(ScheduleResponseDto.class);
    }

    private static String createSelectQueryFilteringBy(LocalDate updatedAt, Long authorId) {
        String sql = "SELECT s.id AS schedule_id, " +
                "s.author_id, " +
                "a.name AS author_name, " +
                "s.task, " +
                "s.created_at, " +
                "s.updated_at " +
                "FROM schedule s " +
                "JOIN author a ON s.author_id = a.id ";

        if (isAnyNotNull(updatedAt, authorId)) {
            sql += "WHERE ";
        }

        if (updatedAt != null) {
            sql += "DATE(s.updated_at) = :updatedAt ";
        }

        if (areBothNotNull(updatedAt, authorId)) {
            sql += "AND ";
        }

        if (authorId != null) {
            sql += "s.author_id = :authorId ";
        }

        sql += "ORDER BY s.updated_at DESC LIMIT :limit OFFSET :offset";

        return sql;
    }

    private static boolean isAnyNotNull(LocalDate updatedAt, Long authorId) {
        return updatedAt != null || authorId != null;
    }

    private static boolean areBothNotNull(LocalDate updatedAt, Long authorId) {
        return updatedAt != null && authorId != null;
    }
}
