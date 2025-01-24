package com.example.scheduler.repository;

import com.example.scheduler.controller.dto.AuthorResponseDto;
import com.example.scheduler.entity.Author;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AuthorRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Author author) {
        String sql = "INSERT INTO author (name, email) VALUES (:name, :email)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", author.getName())
                .addValue("email", author.getEmail());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);

        long id = keyHolder.getKey().longValue();
        author.setId(id);
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
    public Optional<AuthorResponseDto> findDtoById(Long authorId) {
        String sql = "SELECT * FROM author WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", authorId);

        try {
            AuthorResponseDto authorDto = jdbcTemplate.queryForObject(sql, params, authorDtoMapper());
            return Optional.of(authorDto);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<AuthorResponseDto> authorDtoMapper() {
        return BeanPropertyRowMapper.newInstance(AuthorResponseDto.class);
    }
}
