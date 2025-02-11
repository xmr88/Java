-- Create users table
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create channels table
CREATE TABLE channels (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          owner_id BIGINT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create channel_members table
CREATE TABLE channel_members (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 channel_id BIGINT NOT NULL,
                                 user_id BIGINT NOT NULL,
                                 role VARCHAR(50) DEFAULT 'GUEST',
                                 FOREIGN KEY (channel_id) REFERENCES channels(id) ON DELETE CASCADE,
                                 FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create messages table
CREATE TABLE messages (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          sender_id BIGINT NOT NULL,
                          channel_id BIGINT,
                          recipient_id BIGINT,
                          content TEXT NOT NULL,
                          sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
                          FOREIGN KEY (channel_id) REFERENCES channels(id) ON DELETE CASCADE,
                          FOREIGN KEY (recipient_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create friends table
CREATE TABLE friends (
                         user_id BIGINT NOT NULL,
                         friend_id BIGINT NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         PRIMARY KEY (user_id, friend_id),
                         FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                         FOREIGN KEY (friend_id) REFERENCES users(id) ON DELETE CASCADE
);