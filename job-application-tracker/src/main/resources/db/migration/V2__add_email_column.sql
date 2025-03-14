-- Step 1: Add the email column without NOT NULL constraint
ALTER TABLE users
    ADD COLUMN email VARCHAR(255) UNIQUE;

-- Step 2: (Optional) Set email to NULL for existing users (no default value)
UPDATE users
SET email = NULL
WHERE email IS NULL;
