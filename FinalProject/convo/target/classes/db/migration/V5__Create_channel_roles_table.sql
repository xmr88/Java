CREATE TABLE channel_roles (
                               channel_id INT NOT NULL,
                               user_id INT NOT NULL,
                               role VARCHAR(20) NOT NULL,
                               PRIMARY KEY (channel_id, user_id),
                               FOREIGN KEY (channel_id) REFERENCES channels(id) ON DELETE CASCADE,
                               FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
