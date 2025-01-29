CREATE TABLE messages (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          content VARCHAR(500) NOT NULL,
                          timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          author_id INT NOT NULL,
                          channel_id INT,
                          recipient_id INT,
                          FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE,
                          FOREIGN KEY (channel_id) REFERENCES channels(id) ON DELETE CASCADE,
                          FOREIGN KEY (recipient_id) REFERENCES users(id) ON DELETE CASCADE
);
