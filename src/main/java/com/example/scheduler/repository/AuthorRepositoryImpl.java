package com.example.scheduler.repository;

import com.example.scheduler.dto.AuthorRequestDto;
import com.example.scheduler.entity.Author;
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
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AuthorRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public long save(AuthorRequestDto authorRequestDto) {
        String sql = "INSERT INTO author (name, email) VALUES (:name, :email)";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(authorRequestDto);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);

        return keyHolder.getKey().longValue();
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
    public Optional<Author> findById(Long authorId) {
        String sql = "SELECT * FROM author WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", authorId);

        try {
            Author author = jdbcTemplate.queryForObject(sql, params, authorRowMapper());
            return Optional.of(author);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Author> authorRowMapper() {
        return BeanPropertyRowMapper.newInstance(Author.class);
    }
}
