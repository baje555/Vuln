-- Create database
CREATE DATABASE IF NOT EXISTS vulnlab_db;
USE vulnlab_db;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'user',
    bio TEXT
);

-- Create flags table
CREATE TABLE IF NOT EXISTS flags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    level INT NOT NULL UNIQUE,
    flag VARCHAR(255) NOT NULL
);

-- Create files table
CREATE TABLE IF NOT EXISTS files (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    filename VARCHAR(255) NOT NULL,
    owner_id BIGINT NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users(id)
);

-- Insert sample users
INSERT INTO users (username, password, role, bio) VALUES
('admin', 'admin123', 'admin', 'Administrator account'),
('user1', 'password1', 'user', 'Test user 1'),
('user2', 'password2', 'user', 'Test user 2'),
('user3', 'password3', 'user', 'Test user 3');

-- Insert flags
INSERT INTO flags (level, flag) VALUES
(1, 'flag{sql_injection_success}'),
(2, 'flag{xss_reflected}'),
(3, 'flag{stored_xss}'),
(4, 'flag{idor_access}'),
(5, 'flag{file_upload_rce}'),
(6, 'flag{command_injection}'),
(7, 'flag{admin_access}'),
(8, 'flag{csrf_success}'),
(9, 'flag{ssti_rce}'),
(10, 'flag{final_root}');
