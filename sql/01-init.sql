CREATE DATABASE IF NOT EXISTS jpa
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE user
(
    id          BIGINT AUTO_INCREMENT,
    username    VARCHAR(255)                        NOT NULL UNIQUE,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    password    VARCHAR(255)                        NOT NULL,
    enable      BOOLEAN   DEFAULT TRUE              NOT NULL,
    PRIMARY KEY (id)
) COMMENT ='user information';

CREATE TABLE permission
(
    id   BIGINT AUTO_INCREMENT,
    code VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) COMMENT ='user permission';

CREATE TABLE role
(
    id   BIGINT AUTO_INCREMENT,
    code VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) COMMENT ='user role';

CREATE TABLE role_permission
(
    id            BIGINT AUTO_INCREMENT,
    role_id       BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (id)
) COMMENT ='user role_permission';

CREATE TABLE user_role
(
    id      BIGINT AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (id)
) COMMENT ='user user_role';
