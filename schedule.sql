DROP DATABASE IF EXISTS scheduler;
CREATE DATABASE scheduler;

USE scheduler;

CREATE TABLE schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    author_name VARCHAR(30)	NOT NULL,
    password VARCHAR(30) NOT NULL,
    task TEXT NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL
);