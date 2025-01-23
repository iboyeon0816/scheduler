package com.example.scheduler.repository;

import com.example.scheduler.entity.Schedule;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final SimpleJdbcInsert jdbcInsert;

    public ScheduleRepositoryImpl(DataSource dataSource) {
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
}
