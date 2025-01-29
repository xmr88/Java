CREATE TABLE channels (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(50) NOT NULL UNIQUE,
                          owner_id INT NOT NULL,
                          FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE channel_users (
                               channel_id INT NOT NULL,
                               user_id INT NOT NULL,
                               PRIMARY KEY (channel_id, user_id),
                               FOREIGN KEY (channel_id) REFERENCES channels(id) ON DELETE CASCADE,
                               FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
