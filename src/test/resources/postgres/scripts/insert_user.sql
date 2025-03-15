INSERT INTO users (
    username,
    email,
    password_hash,
    first_name,
    last_name,
    bio,
    profile_picture_url,
    created_at,
    updated_at
)
VALUES (
           'johndoe',
           'johndoe@example.com',
           'hashedpassword123',
           'John',
           'Doe',
           'A short bio about John.',
           'http://example.com/john.jpg',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );
