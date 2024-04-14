-- Insert data for 4 members
INSERT INTO accounts (first_name, last_name, username, password, account_type)
VALUES ('Member1', 'Lastname', 'm1', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 'Member'),
       ('Member2', 'Lastname', 'm2', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 'Member'),
       ('Member3', 'Lastname', 'm3', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 'Member'),
       ('Member4', 'Lastname', 'm4', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 'Member');

-- Insert data for 4 trainers
INSERT INTO accounts (first_name, last_name, username, password, account_type)
VALUES ('Trainer1', 'Lastname', 't1', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 'Trainer'),
       ('Trainer2', 'Lastname', 't2', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 'Trainer'),
       ('Trainer3', 'Lastname', 't3', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 'Trainer'),
       ('Trainer4', 'Lastname', 't4', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 'Trainer');

-- Insert data for 4 staff
INSERT INTO accounts (first_name, last_name, username, password, account_type)
VALUES ('Staff1', 'Lastname', 's1', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 'Staff'),
       ('Staff2', 'Lastname', 's2', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 'Staff'),
       ('Staff3', 'Lastname', 's3', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 'Staff'),
       ('Staff4', 'Lastname', 's4', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 'Staff');

-- Insert data for 10 rooms
INSERT INTO rooms (trainer_id, h_8_9, h_9_10, h_10_11, h_11_12, h_12_13, h_13_14, h_14_15, h_15_16, h_16_17, h_17_18)
VALUES (NULL, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE),
       (NULL, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE),
       (NULL, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE),
       (NULL, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE),
       (NULL, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE),
       (NULL, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE),
       (NULL, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE),
       (NULL, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE),
       (NULL, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE),
       (NULL, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE);

-- Insert default equipment if not already present
INSERT INTO equipment (name)
SELECT DISTINCT name
FROM (VALUES ('HS Chest Press'), ('Smith Machine'), ('Pec Dec'), ('Leg Extensions'),
             ('Hamstring Curls'), ('Shoulder Press'), ('Cables'), ('LatPullDown'), ('Seated Cable Rows'),
             ('Treadmill'), ('Bicycle'), ('StairMaster'), ('Leg Press')) AS defaultEquipment(name)
WHERE NOT EXISTS (
    SELECT 1 FROM equipment WHERE name = defaultEquipment.name
);

-- Insert classes if not already present
INSERT INTO classes (class_name, h_8_9, h_9_10, h_10_11, h_11_12, h_12_13, h_13_14, h_14_15, h_15_16, h_16_17, h_17_18)
VALUES ('Yoga', TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE),
       ('Calisthenics', TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE),
       ('Weight Lifting', TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE),
       ('Cardio', TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE);
