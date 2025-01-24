DROP DATABASE IF EXISTS scheduler;
CREATE DATABASE scheduler;

USE scheduler;

CREATE TABLE Author (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    email VARCHAR(30) NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL
);

CREATE TABLE schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    author_id BIGINT NOT NULL REFERENCES Author (id),
    password VARCHAR(30) NOT NULL,
    task TEXT NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL
);