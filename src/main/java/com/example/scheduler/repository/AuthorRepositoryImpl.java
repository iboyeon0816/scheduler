package com.example.scheduler.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AuthorRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public boolean existsById(Long authorId) {
        String sql = "SELECT CASE WHEN COUNT(*) > 0 THEN true " +
                "ELSE false END " +
                "FROM author " +
                "WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", authorId);

        return jdbcTemplate.queryForObject(sql, params, Boolean.class);
    }

    @Override
    public String findNameById(Long authorId) {
        String sql = "SELECT name FROM author WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", authorId);

        return jdbcTemplate.queryForObject(sql, params, String.class);
    }
}
