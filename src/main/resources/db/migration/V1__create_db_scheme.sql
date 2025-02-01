DROP TABLE IF EXISTS td_users;
DROP TABLE IF EXISTS td_user_roles;
DROP TABLE IF EXISTS tc_user_friends;
DROP TABLE IF EXISTS td_channels;
DROP TABLE IF EXISTS tc_channel_users;
DROP TABLE IF EXISTS tc_channel_admins;
DROP TABLE IF EXISTS td_messages;

-- Create the td_users table
CREATE TABLE IF NOT EXISTS td_users (
                                        id INT PRIMARY KEY AUTO_INCREMENT,
                                        username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_active INT DEFAULT 1
    );

-- Create the td_user_roles table
CREATE TABLE IF NOT EXISTS td_user_roles (
                                             id INT PRIMARY KEY AUTO_INCREMENT,
                                             user_id INT NOT NULL,
                                             role VARCHAR(255) NOT NULL,
    is_active INT DEFAULT 1,
    FOREIGN KEY (user_id) REFERENCES td_users (id) ON DELETE CASCADE
    );

-- Create the tc_user_friends table for many-to-many relationships
CREATE TABLE IF NOT EXISTS tc_user_friends (
                                               id INT PRIMARY KEY AUTO_INCREMENT,
                                               user_id INT NOT NULL,
                                               friend_id INT NOT NULL,
                                               is_active INT DEFAULT 1,
                                               UNIQUE(user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES td_users (id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES td_users (id) ON DELETE CASCADE
    );

-- Create the td_channels table
CREATE TABLE IF NOT EXISTS td_channels (
                                           id INT PRIMARY KEY AUTO_INCREMENT,
                                           name VARCHAR(255) NOT NULL UNIQUE,
    owner_id INT NOT NULL,
    is_active INT DEFAULT 1,
    FOREIGN KEY (owner_id) REFERENCES td_users (id) ON DELETE CASCADE
    );

-- Create the tc_channel_users table for many-to-many relationships
CREATE TABLE IF NOT EXISTS tc_channel_users (
                                                id INT PRIMARY KEY AUTO_INCREMENT,
                                                channel_id INT NOT NULL,
                                                user_id INT NOT NULL,
                                                role VARCHAR(255) DEFAULT 'GUEST',
    is_active INT DEFAULT 1,
    UNIQUE(channel_id, user_id),
    FOREIGN KEY (channel_id) REFERENCES td_channels (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES td_users (id) ON DELETE CASCADE
    );

-- Create the tc_channel_admins table for many-to-many relationships
CREATE TABLE IF NOT EXISTS tc_channel_admins (
                                                 id INT PRIMARY KEY AUTO_INCREMENT,
                                                 channel_id INT NOT NULL,
                                                 user_id INT NOT NULL,
                                                 is_active INT DEFAULT 1,
                                                 UNIQUE(channel_id, user_id),
    FOREIGN KEY (channel_id) REFERENCES td_channels (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES td_users (id) ON DELETE CASCADE
    );

-- Create the td_messages table
CREATE TABLE IF NOT EXISTS td_messages (
                                           id INT PRIMARY KEY AUTO_INCREMENT,
                                           sender_id INT NOT NULL,
                                           receiver_id INT,
                                           channel_id INT,
                                           content TEXT NOT NULL,
                                           timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                           is_active INT DEFAULT 1,
                                           FOREIGN KEY (sender_id) REFERENCES td_users (id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES td_users (id) ON DELETE CASCADE,
    FOREIGN KEY (channel_id) REFERENCES td_channels (id) ON DELETE CASCADE
    );

-- Insert initial data for testing

-- Insert a default user (admin user)
INSERT INTO td_users (username, password, is_active) VALUES ('admin', 'admin123', 1);

-- Insert default roles for the admin user
INSERT INTO td_user_roles (user_id, role, is_active) VALUES (1, 'OWNER', 1);

-- Insert a default channel owned by the admin user
INSERT INTO td_channels (name, owner_id, is_active) VALUES ('General', 1, 1);

-- Add the admin user to the default channel as a participant
INSERT INTO tc_channel_users (channel_id, user_id, role, is_active) VALUES (1, 1, 'OWNER', 1);