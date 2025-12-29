-- Portfolio Backend Database Setup
-- Run this script to create or update the admin user

-- IMPORTANT: Generate your BCrypt hash first!
-- Visit: https://bcrypt-generator.com/
-- Enter your password and copy the generated hash

-- To CREATE admin user (replace <BCRYPT_HASH> with your generated hash):
INSERT INTO admins (username, password, email) 
VALUES ('admin', '<BCRYPT_HASH>', 'admin@ragingscout97.in')
ON CONFLICT (username) DO NOTHING;

-- To UPDATE existing admin password (replace <BCRYPT_HASH> with your generated hash):
-- UPDATE admins 
-- SET password = '<BCRYPT_HASH>' 
-- WHERE username = 'admin';

-- Example for password 'Admin@321!':
-- Generate hash at https://bcrypt-generator.com/ and replace <BCRYPT_HASH> above

