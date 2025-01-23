package com.example.scheduler.repository;

import com.example.scheduler.entity.Schedule;
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
    public Schedule save(Schedule schedule) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(schedule);
        Number id = jdbcInsert.executeAndReturnKey(params);
        schedule.setId(id.longValue());
        return schedule;
    }

    @Override
    public List<Schedule> findAll(LocalDate updatedAt, String authorName) {
        String sql = "SELECT * FROM schedule ";

        if (updatedAt != null || authorName != null) {
            sql += "WHERE ";
        }

        if (updatedAt != null) {
            sql += "DATE(updated_at) = :updatedAt ";
        }

        if (updatedAt != null && authorName != null) {
            sql += "AND ";
        }

        if (authorName != null) {
            sql += "author_name = :authorName ";
        }

        sql += "ORDER BY updated_at DESC";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("updatedAt", updatedAt)
                .addValue("authorName", authorName);

        return jdbcTemplate.query(sql, params, scheduleRowMapper());
    }

    @Override
    public Optional<Schedule> findById(Long scheduleId) {
        String sql = "SELECT * FROM schedule WHERE id = :id";

        try {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", scheduleId);

            Schedule schedule = jdbcTemplate.queryForObject(sql, params, scheduleRowMapper());
            return Optional.of(schedule);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateById(Long scheduleId, Schedule schedule) {
        String sql = "UPDATE schedule " +
                "SET author_name = :authorName, task = :task, updated_at = :updatedAt " +
                "WHERE id = :id";

        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(schedule);
        jdbcTemplate.update(sql, params);
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return BeanPropertyRowMapper.newInstance(Schedule.class);
    }
}
