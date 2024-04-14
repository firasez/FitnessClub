-- Table: accounts
CREATE TABLE IF NOT EXISTS accounts (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(200) NOT NULL,
    account_type VARCHAR(20) NOT NULL
);

-- Table: memberInfo
CREATE TABLE IF NOT EXISTS memberInfo (
    id INT,
    FOREIGN KEY (id) REFERENCES accounts(id),
    body_weight DECIMAL(10,2) NOT NULL,
    height DECIMAL(10,2) NOT NULL,
    goal_weight DECIMAL(10,2) NOT NULL,
    age INT,
    gender VARCHAR(20) NOT NULL,
    trainer_id INT,
    FOREIGN KEY (trainer_id) REFERENCES accounts(id),
    booked_time VARCHAR(50),
    bill_to_pay BOOLEAN DEFAULT TRUE
);

-- Table: bookedClasses
CREATE TABLE IF NOT EXISTS bookedClasses (
    id SERIAL PRIMARY KEY,
    member_id INT,
    FOREIGN KEY (member_id) REFERENCES accounts(id),
    class_name VARCHAR(100) NOT NULL,
    class_time VARCHAR(50) NOT NULL
);

-- Table: foodInfo
CREATE TABLE IF NOT EXISTS foodInfo (
    id INT,
    FOREIGN KEY (id) REFERENCES accounts(id),
    food_name VARCHAR(50) NOT NULL,
    calories INT NOT NULL,
    protein DECIMAL(10,2) NOT NULL,
    carbs DECIMAL(10,2) NOT NULL,
    sugar DECIMAL(10,2) NOT NULL,
    fats DECIMAL(10,2) NOT NULL
);

-- Table: exercises
CREATE TABLE IF NOT EXISTS exercises (
    id INT,
    FOREIGN KEY (id) REFERENCES accounts(id),
    workout_name VARCHAR(100) NOT NULL,
    reps INT NOT NULL,
    sets INT NOT NULL,
    calories_burnt DECIMAL(10,2) NOT NULL
);

-- Table: cardio
CREATE TABLE IF NOT EXISTS cardio (
    id INT,
    FOREIGN KEY (id) REFERENCES accounts(id),
    exercise_name VARCHAR(100) NOT NULL,
    duration_minutes INT NOT NULL,
    steps INT NOT NULL,
    distance DECIMAL(10,2) NOT NULL,
    calories_burnt DECIMAL(10,2) NOT NULL
);

-- Table: trainer_availability
CREATE TABLE IF NOT EXISTS trainer_availability (
    trainer_id INT,
    availability_date DATE,
    h_8_9 BOOLEAN DEFAULT FALSE,
    h_9_10 BOOLEAN DEFAULT FALSE,
    h_10_11 BOOLEAN DEFAULT FALSE,
    h_11_12 BOOLEAN DEFAULT FALSE,
    h_12_13 BOOLEAN DEFAULT FALSE,
    h_13_14 BOOLEAN DEFAULT FALSE,
    h_14_15 BOOLEAN DEFAULT FALSE,
    h_15_16 BOOLEAN DEFAULT FALSE,
    h_16_17 BOOLEAN DEFAULT FALSE,
    h_17_18 BOOLEAN DEFAULT FALSE
);

-- Table: classes
CREATE TABLE IF NOT EXISTS classes (
    class_name VARCHAR(50) PRIMARY KEY,
    h_8_9 BOOLEAN DEFAULT FALSE,
    h_9_10 BOOLEAN DEFAULT FALSE,
    h_10_11 BOOLEAN DEFAULT FALSE,
    h_11_12 BOOLEAN DEFAULT FALSE,
    h_12_13 BOOLEAN DEFAULT FALSE,
    h_13_14 BOOLEAN DEFAULT FALSE,
    h_14_15 BOOLEAN DEFAULT FALSE,
    h_15_16 BOOLEAN DEFAULT FALSE,
    h_16_17 BOOLEAN DEFAULT FALSE,
    h_17_18 BOOLEAN DEFAULT FALSE
);

-- Table: workout_classes
CREATE TABLE IF NOT EXISTS workout_classes (
    class_name VARCHAR(50) PRIMARY KEY,
    h_8_9 BOOLEAN DEFAULT FALSE,
    h_9_10 BOOLEAN DEFAULT FALSE,
    h_10_11 BOOLEAN DEFAULT FALSE,
    h_11_12 BOOLEAN DEFAULT FALSE,
    h_12_13 BOOLEAN DEFAULT FALSE,
    h_13_14 BOOLEAN DEFAULT FALSE,
    h_14_15 BOOLEAN DEFAULT FALSE,
    h_15_16 BOOLEAN DEFAULT FALSE,
    h_16_17 BOOLEAN DEFAULT FALSE,
    h_17_18 BOOLEAN DEFAULT FALSE
);

-- Table: rooms
CREATE TABLE IF NOT EXISTS rooms (
    room_number SERIAL PRIMARY KEY UNIQUE,
    trainer_id INT,
    FOREIGN KEY (trainer_id) REFERENCES accounts(id),
    h_8_9 BOOLEAN DEFAULT FALSE,
    h_9_10 BOOLEAN DEFAULT FALSE,
    h_10_11 BOOLEAN DEFAULT FALSE,
    h_11_12 BOOLEAN DEFAULT FALSE,
    h_12_13 BOOLEAN DEFAULT FALSE,
    h_13_14 BOOLEAN DEFAULT FALSE,
    h_14_15 BOOLEAN DEFAULT FALSE,
    h_15_16 BOOLEAN DEFAULT FALSE,
    h_16_17 BOOLEAN DEFAULT FALSE,
    h_17_18 BOOLEAN DEFAULT FALSE
);

-- Table: equipment
CREATE TABLE IF NOT EXISTS equipment (
    name VARCHAR(50),
    status VARCHAR(50) DEFAULT 'Working',
    serial_no SERIAL
);

-- Table: member_records
CREATE TABLE IF NOT EXISTS member_records (
    id INT,
    workout_name VARCHAR(100) NOT NULL,
    record VARCHAR(255) NOT NULL
);
