package com.example.scheduler.repository;

import com.example.scheduler.controller.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;
import com.example.scheduler.service.dto.ScheduleUpdateParam;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("schedule") // 데이터를 삽입할 테이블 지정
                .usingGeneratedKeyColumns("id"); // 자동 증가 키 값을 반환받을 수 있게 지정
    }

    @Override
    public void save(Schedule schedule) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(schedule);
        Number id = jdbcInsert.executeAndReturnKey(params);
        schedule.setId(id.longValue());
    }

    @Override
    public void updateById(Long scheduleId, ScheduleUpdateParam updateParam) {
        String sql = "UPDATE schedule " +
                "SET task = :task, updated_at = :updatedAt " +
                "WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("task", updateParam.getTask())
                .addValue("updatedAt", updateParam.getUpdatedAt())
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
