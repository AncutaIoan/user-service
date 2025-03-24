TRUNCATE TABLE users;

INSERT INTO users (id, username, email, password_hash, first_name, last_name, bio, profile_picture_url, created_at, updated_at) VALUES (1, 'john_doesds', 'johsds@example.com', '$2a$10$L0UwA/oPB45ZaLt0BHrYY.BuPkX/7SvDqEgsZPl.BS7OHUZ.d.ID.', 'John', 'Doe', 'I love Kotlin!', 'https://example.com/profile.jpg', '2025-03-17 23:18:45.364584', '2025-03-17 23:18:45.364584');