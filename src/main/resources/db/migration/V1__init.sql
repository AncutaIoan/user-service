CREATE TABLE IF NOT EXISTS users (
                       id SERIAL PRIMARY KEY,                        -- Auto-incrementing primary key
                       username VARCHAR(255) NOT NULL UNIQUE,        -- Username (unique and not null)
                       email VARCHAR(255) NOT NULL UNIQUE,           -- Email (unique and not null)
                       password_hash VARCHAR(255) NOT NULL,          -- Password hash (not null)
                       first_name VARCHAR(255),                      -- First name (nullable)
                       last_name VARCHAR(255),                       -- Last name (nullable)
                       bio TEXT,                                     -- Bio (nullable)
                       profile_picture_url VARCHAR(255),             -- Profile picture URL (nullable)
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Created timestamp (default to current timestamp)
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Updated timestamp (auto-updated on change)
);
