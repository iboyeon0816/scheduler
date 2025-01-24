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
    public void save(Schedule schedule) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(schedule);
        Number id = jdbcInsert.executeAndReturnKey(params);
        schedule.setId(id.longValue());
    }

    @Override
    public List<Schedule> findAll(LocalDate updatedAt, Long authorId, int page, int size) {
        String sql = createSelectQueryFilteringBy(updatedAt, authorId);

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("updatedAt", updatedAt)
                .addValue("authorId", authorId)
                .addValue("limit", size)
                .addValue("offset", (page - 1) * size);

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

    // TODO: schedule -> dto 변경
    @Override
    public void updateById(Long scheduleId, Schedule schedule) {
        String sql = "UPDATE schedule " +
                "SET author_name = :authorName, task = :task, updated_at = :updatedAt " +
                "WHERE id = :id";

        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(schedule);
        jdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteById(Long scheduleId) {
        String sql = "DELETE FROM schedule WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", scheduleId);
        jdbcTemplate.update(sql, params);
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return BeanPropertyRowMapper.newInstance(Schedule.class);
    }

    private static String createSelectQueryFilteringBy(LocalDate updatedAt, Long authorId) {
        String sql = "SELECT * FROM schedule ";

        if (isAnyNotNull(updatedAt, authorId)) {
            sql += "WHERE ";
        }

        if (updatedAt != null) {
            sql += "DATE(updated_at) = :updatedAt ";
        }

        if (areBothNotNull(updatedAt, authorId)) {
            sql += "AND ";
        }

        if (authorId != null) {
            sql += "author_id = :authorId ";
        }

        sql += "ORDER BY updated_at DESC LIMIT :limit OFFSET :offset";

        return sql;
    }

    private static boolean isAnyNotNull(LocalDate updatedAt, Long authorId) {
        return updatedAt != null || authorId != null;
    }

    private static boolean areBothNotNull(LocalDate updatedAt, Long authorId) {
        return updatedAt != null && authorId != null;
    }
}
