CREATE TABLE IF NOT EXISTS td_users(
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(128),
    full_name VARCHAR(256),
    is_active INT DEFAULT 1
)