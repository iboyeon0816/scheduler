package com.example.scheduler.repository;

import com.example.scheduler.entity.Author;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public AuthorRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("author") // 데이터를 삽입할 테이블 지정
                .usingGeneratedKeyColumns("id"); // 자동 증가 키 값을 반환받을 수 있게 지정
    }

    @Override
    public void save(Author author) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(author);
        Number id = jdbcInsert.executeAndReturnKey(params);
        author.setId(id.longValue());
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
}
