import java.net.URL;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
public class Model {
    private Connection connection;
    private static final String url = "jdbc:postgresql://localhost:5432/FitnessClub";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    public Model() {
        formConnection();
        createTable();
    }

    public void formConnection() {
        try {
            connection = DriverManager.getConnection(url, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        try (Statement statement = connection.createStatement()) {

            String createMemberInfoTableQuery = "CREATE TABLE IF NOT EXISTS memberInfo (" +
                    "id INT," +
                    "FOREIGN KEY (id) REFERENCES accounts(id)," +
                    "body_weight DECIMAL(10,2) NOT NULL," +
                    "height DECIMAL(10,2) NOT NULL," +
                    "goal_weight DECIMAL(10,2) NOT NULL," +
                    "age INT," +
                    "gender VARCHAR(20) NOT NULL," +
                    "trainer_id INT," +
                    "FOREIGN KEY (trainer_id) REFERENCES accounts(id)," +
                    "booked_time VARCHAR(50)," +
                    "bill_to_pay BOOLEAN DEFAULT TRUE" +
                    ")";

            String createBookedClassesTableQuery = "CREATE TABLE IF NOT EXISTS bookedClasses (" +
                    "id SERIAL PRIMARY KEY," +
                    "member_id INT," +
                    "FOREIGN KEY (member_id) REFERENCES accounts(id)," +
                    "class_name VARCHAR(100) NOT NULL," +
                    "class_time VARCHAR(50) NOT NULL" +
                    ")";

            String createfoodInfoQuery = "CREATE TABLE IF NOT EXISTS foodInfo (" +
                    "id INT," +
                    "FOREIGN KEY (id) REFERENCES accounts(id)," +
                    "food_name VARCHAR(50) NOT NULL," +
                    "calories INT  NOT NULL," +
                    "protein DECIMAL(10,2) NOT NULL," +
                    "carbs DECIMAL(10,2) NOT NULL," +
                    "sugar DECIMAL(10,2) NOT NULL," +
                    "fats DECIMAL(10,2) NOT NULL" +
                    ")";

            String createAccountsTableQuery = "CREATE TABLE IF NOT EXISTS accounts (" +
                    "id SERIAL PRIMARY KEY," +
                    "first_name VARCHAR(50) NOT NULL," +
                    "last_name VARCHAR(50) NOT NULL," +
                    "username VARCHAR(50) UNIQUE NOT NULL," +
                    "password VARCHAR(200) NOT NULL," +
                    "account_type VARCHAR(20) NOT NULL" +
                    ")";
            String createWeightLiftingTable = "CREATE TABLE IF NOT EXISTS exercises (" +
                    "id INT," +
                    "FOREIGN KEY (id) REFERENCES accounts(id)," +
                    "workout_name VARCHAR(100) NOT NULL," +
                    "reps INT NOT NULL," +
                    "sets INT NOT NULL," +
                    "calories_burnt DECIMAL(10,2) NOT NULL" +
                    ")";
            String createCardioTable = "CREATE TABLE IF NOT EXISTS cardio (" +
                    "id INT," +
                    "FOREIGN KEY (id) REFERENCES accounts(id)," +
                    "exercise_name VARCHAR(100) NOT NULL," +
                    "duration_minutes INT NOT NULL," +
                    "steps INT NOT NULL," +
                    "distance DECIMAL(10,2) NOT NULL," +
                    "calories_burnt DECIMAL(10,2) NOT NULL" +
                    ")";
            String createTableQuery = "CREATE TABLE IF NOT EXISTS trainer_availability ("
                    + "trainer_id INT,"
                    + "availability_date DATE,"
                    + "h_8_9 BOOLEAN DEFAULT FALSE,"
                    + "h_9_10 BOOLEAN DEFAULT FALSE,"
                    + "h_10_11 BOOLEAN DEFAULT FALSE,"
                    + "h_11_12 BOOLEAN DEFAULT FALSE,"
                    + "h_12_13 BOOLEAN DEFAULT FALSE,"
                    + "h_13_14 BOOLEAN DEFAULT FALSE,"
                    + "h_14_15 BOOLEAN DEFAULT FALSE,"
                    + "h_15_16 BOOLEAN DEFAULT FALSE,"
                    + "h_16_17 BOOLEAN DEFAULT FALSE,"
                    + "h_17_18 BOOLEAN DEFAULT FALSE"
                    + ")";

            String createClassQuery = "CREATE TABLE IF NOT EXISTS classes ("
                    + "class_name VARCHAR(50) PRIMARY KEY,"
                    + "h_8_9 BOOLEAN DEFAULT FALSE,"
                    + "h_9_10 BOOLEAN DEFAULT FALSE,"
                    + "h_10_11 BOOLEAN DEFAULT FALSE,"
                    + "h_11_12 BOOLEAN DEFAULT FALSE,"
                    + "h_12_13 BOOLEAN DEFAULT FALSE,"
                    + "h_13_14 BOOLEAN DEFAULT FALSE,"
                    + "h_14_15 BOOLEAN DEFAULT FALSE,"
                    + "h_15_16 BOOLEAN DEFAULT FALSE,"
                    + "h_16_17 BOOLEAN DEFAULT FALSE,"
                    + "h_17_18 BOOLEAN DEFAULT FALSE"
                    + ")";
            String createWorkoutClassQuery = "CREATE TABLE IF NOT EXISTS workout_classes ("
                    + "class_name VARCHAR(50) PRIMARY KEY,"
                    + "room_id INT,"
                    + "FOREIGN KEY (room_id) REFERENCES rooms(room_number),"
                    + "h_8_9 BOOLEAN DEFAULT FALSE,"
                    + "h_9_10 BOOLEAN DEFAULT FALSE,"
                    + "h_10_11 BOOLEAN DEFAULT FALSE,"
                    + "h_11_12 BOOLEAN DEFAULT FALSE,"
                    + "h_12_13 BOOLEAN DEFAULT FALSE,"
                    + "h_13_14 BOOLEAN DEFAULT FALSE,"
                    + "h_14_15 BOOLEAN DEFAULT FALSE,"
                    + "h_15_16 BOOLEAN DEFAULT FALSE,"
                    + "h_16_17 BOOLEAN DEFAULT FALSE,"
                    + "h_17_18 BOOLEAN DEFAULT FALSE"
                    + ")";

            String createRoomQuery = "CREATE TABLE IF NOT EXISTS rooms ("
                    + "room_number SERIAL PRIMARY KEY UNIQUE,"
                    + "trainer_id INT,"
                    + "FOREIGN KEY (trainer_id) REFERENCES accounts(id),"
                    + "h_8_9 BOOLEAN DEFAULT FALSE,"
                    + "h_9_10 BOOLEAN DEFAULT FALSE,"
                    + "h_10_11 BOOLEAN DEFAULT FALSE,"
                    + "h_11_12 BOOLEAN DEFAULT FALSE,"
                    + "h_12_13 BOOLEAN DEFAULT FALSE,"
                    + "h_13_14 BOOLEAN DEFAULT FALSE,"
                    + "h_14_15 BOOLEAN DEFAULT FALSE,"
                    + "h_15_16 BOOLEAN DEFAULT FALSE,"
                    + "h_16_17 BOOLEAN DEFAULT FALSE,"
                    + "h_17_18 BOOLEAN DEFAULT FALSE"
                    + ")";

            String createEquipmentInfoTableQuery = "CREATE TABLE IF NOT EXISTS equipment ("
                    + "name VARCHAR(50),"
                    + "status VARCHAR(50) DEFAULT 'Working',"
                    + "serial_no SERIAL"
                    + ")";
            String createMemberExerciseTable = "CREATE TABLE IF NOT EXISTS member_records (" +
                    "id INT ," +
                    "workout_name VARCHAR(100) NOT NULL," +
                    "record VARCHAR(255) NOT NULL" +
                    ")";
            statement.executeUpdate(createTableQuery);
            statement.executeUpdate(createAccountsTableQuery);
            statement.executeUpdate(createfoodInfoQuery);
            statement.executeUpdate(createMemberInfoTableQuery);
            statement.executeUpdate(createWeightLiftingTable);
            statement.executeUpdate(createCardioTable);
            statement.executeUpdate(createClassQuery);
            statement.executeUpdate(createRoomQuery);
            statement.executeUpdate(createEquipmentInfoTableQuery);
            statement.executeUpdate(createMemberExerciseTable);
            statement.executeUpdate(createBookedClassesTableQuery);
            statement.executeUpdate(createWorkoutClassQuery);
            setClass();
            setEquipment();
            setRoom();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAccount(String firstName, String lastName, String username, String password, String accountType) {
        String pass = setPassword(password);
        try {
            formConnection();
            String insertQuery = "INSERT INTO accounts (first_name, last_name, username, password, account_type) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, pass);
            preparedStatement.setString(5, accountType);
            preparedStatement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> addRooms(ArrayList<String> times, int trainer_id) {
        ArrayList<Integer> availableRooms = new ArrayList<>();
        try {
            formConnection();
            for (String time : times) {
                String[] hours = time.split("-");
                String startHour = hours[0];
                String endHour = hours[1];

                String selectQuery = "SELECT room_number FROM rooms WHERE trainer_id IS NULL AND " +
                        "h_" + startHour + "_" + endHour + " = FALSE";
                PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = selectStatement.executeQuery();

                while (resultSet.next()) {
                    availableRooms.add(resultSet.getInt("room_number"));
                }
            }

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableRooms;
    }

    public void setRoom() {
        String insertRoomQuery = "INSERT INTO rooms (room_number) VALUES (?) ON CONFLICT DO NOTHING";

        try (Connection conn = DriverManager.getConnection(url, USER, PASSWORD)) {
            try (PreparedStatement insertStatement = conn.prepareStatement(insertRoomQuery)) {
                for (int i = 1; i <= 10; i++) {
                    insertStatement.setInt(1, i);
                    insertStatement.executeUpdate();
                }
            }

            System.out.println("Rooms added successfully.");

        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }
    }


    public void addInfo(int id, double weight, double height, double goalWeight, int age, String gender) {
        try {
            formConnection();
            String insertQuery = "INSERT INTO memberInfo (id, body_weight, height, goal_weight, age, gender) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setDouble(2, weight);
            preparedStatement.setDouble(3, height);
            preparedStatement.setDouble(4, goalWeight);
            preparedStatement.setInt(5, age);
            preparedStatement.setString(6, gender);
            preparedStatement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addFood(int id, String foodName, int cals, double p, double c, double s, double f) {
        try {
            formConnection();
            String insertQuery = "INSERT INTO foodInfo (id, food_name, calories, protein, carbs, sugar, fats) VALUES (?, ?, ?, ?, ?, ?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, foodName);
            preparedStatement.setInt(3, cals);
            preparedStatement.setDouble(4, p);
            preparedStatement.setDouble(5, c);
            preparedStatement.setDouble(6, s);
            preparedStatement.setDouble(7, f);
            preparedStatement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String setPassword(String password) {
        String passwordHash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            passwordHash = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return passwordHash;
    }

    public boolean verifyPassword(String username, String password) {
        boolean isValid = false;
        try {
            formConnection();
            String selectQuery = "SELECT password FROM accounts WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedHashedPassword = resultSet.getString("password");
                String enteredHashedPassword = setPassword(password);
                isValid = storedHashedPassword.equals(enteredHashedPassword);
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!isValid) {
            System.out.println("Incorrect Password");
        }
        return isValid;
    }

    public boolean verifyUsername(String username) {
        boolean exists = false;
        try {
            formConnection();
            String selectQuery = "SELECT username FROM accounts WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            exists = resultSet.next();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!exists) {
            System.out.println("Username does not exist");
        }
        return exists;
    }

    public boolean verifyAccountType(String username, String type) {
        boolean matches = false;
        try {
            formConnection();
            String selectQuery = "SELECT account_type FROM accounts WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String accountType = resultSet.getString("account_type");
                matches = accountType.equals(type);
            } else {
                System.out.println("Username not found: " + username);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matches;
    }


    public static int getIdByUsername(String username) {
        int id = -1;
        String url = "jdbc:postgresql://localhost:5432/FitnessClub";
        String user = USER;
        String password = PASSWORD;

        String sql = "SELECT id FROM accounts WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public void addWeightLiftingInfo(int accountId, String workoutName, int reps, int sets, double caloriesBurnt) {
        try {
            formConnection();
            String insertQuery = "INSERT INTO exercises (id, workout_name, reps, sets, calories_burnt) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, accountId);
            preparedStatement.setString(2, workoutName);
            preparedStatement.setInt(3, reps);
            preparedStatement.setInt(4, sets);
            preparedStatement.setDouble(5, caloriesBurnt);
            preparedStatement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCardioExercise(int accountId, String exerciseName, int durationMinutes, int steps, double distance, double caloriesBurnt) {
        try {
            formConnection();
            String insertQuery = "INSERT INTO cardio (id, exercise_name, duration_minutes, steps, distance, calories_burnt) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, accountId);
            preparedStatement.setString(2, exerciseName);
            preparedStatement.setInt(3, durationMinutes);
            preparedStatement.setInt(4, steps);
            preparedStatement.setDouble(5, distance);
            preparedStatement.setDouble(6, caloriesBurnt);
            preparedStatement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearExercisesForPerson(int personId) {
        String deleteExercisesQuery = "DELETE FROM exercises WHERE id = ?";
        formConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteExercisesQuery)) {
            preparedStatement.setInt(1, personId);
            preparedStatement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getMemberInfo(int memberId) {
        ResultSet rs = null;
        String query = "SELECT body_weight, height, goal_weight, age, gender FROM memberInfo WHERE id = ?";
        formConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, memberId);
            rs = preparedStatement.executeQuery();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public boolean changeUsername(int memberId, String newUsername, String currentUsername, String password) {

        boolean success = false;

        String updateQuery = "UPDATE accounts SET username = ? WHERE id = ?";
        try {
            if (verifyPassword(currentUsername, password)) {
                formConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(1, newUsername);
                preparedStatement.setInt(2, getIdByUsername(currentUsername));
                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    success = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();
        return success;
    }



    public boolean changePassword(int memberId, String username, String oldPassword, String newPassword, String confirmNewPassword) {
        try {
            if (!verifyPassword(username, oldPassword)) {
                System.out.println("Old password is incorrect.");
                return false;
            }

            if (!newPassword.equals(confirmNewPassword)) {
                System.out.println("New password and confirmation do not match.");
                return false;
            }

            formConnection();
            String newPasswordHash = setPassword(newPassword);
            String updateQuery = "UPDATE accounts SET password = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, newPasswordHash);
            preparedStatement.setInt(2, memberId);
            preparedStatement.executeUpdate();
            System.out.println("Password updated successfully.");

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public boolean deleteAccount(int memberId, String username, String password) {
        boolean bool = false;

        if (verifyPassword(username, password)) {
            try {
                formConnection();

                String deleteMemberInfoQuery = "DELETE FROM memberInfo WHERE id = ?";
                PreparedStatement memberInfoStatement = connection.prepareStatement(deleteMemberInfoQuery);
                memberInfoStatement.setInt(1, memberId);
                memberInfoStatement.executeUpdate();


                String deleteFoodInfoQuery = "DELETE FROM foodInfo WHERE id = ?";
                PreparedStatement foodInfoStatement = connection.prepareStatement(deleteFoodInfoQuery);
                foodInfoStatement.setInt(1, memberId);
                foodInfoStatement.executeUpdate();


                String deleteExercisesQuery = "DELETE FROM exercises WHERE id = ?";
                PreparedStatement exercisesStatement = connection.prepareStatement(deleteExercisesQuery);
                exercisesStatement.setInt(1, memberId);
                exercisesStatement.executeUpdate();

                String deleteCardioQuery = "DELETE FROM cardio WHERE id = ?";
                PreparedStatement cardioStatement = connection.prepareStatement(deleteCardioQuery);
                cardioStatement.setInt(1, memberId);
                cardioStatement.executeUpdate();

                String deleteAccountQuery = "DELETE FROM accounts WHERE id = ?";
                PreparedStatement accountStatement = connection.prepareStatement(deleteAccountQuery);
                accountStatement.setInt(1, memberId);
                accountStatement.executeUpdate();

                closeConnection();
                bool = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return bool;
    }


    protected ArrayList<String> getMembers() {
        ArrayList<String> members = new ArrayList<>();
        try {
            formConnection();
            String selectQuery = "SELECT username FROM accounts WHERE account_type = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, "Member");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                members.add(resultSet.getString("username"));
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;

    }

    protected ArrayList<String> getTrainer() {
        ArrayList<String> trainers = new ArrayList<>();
        try {
            formConnection();
            String selectQuery = "SELECT username FROM accounts WHERE account_type = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, "Trainer");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                trainers.add(resultSet.getString("username"));
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainers;
    }

    protected int getTrainerId(String username) {
        int trainerId = -1;
        try {
            formConnection();
            String selectQuery = "SELECT id FROM accounts WHERE username = ? AND account_type = 'Trainer'";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                trainerId = resultSet.getInt("id");
            } else {
                System.out.println("Trainer ID not found for username: " + username);
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainerId;
    }


    public void addTrainerById(int memberId, String trainerUsername) {
        int trainerId = getTrainerId(trainerUsername);
        try {
            formConnection();
            String updateQuery = "UPDATE memberInfo SET trainer_id = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, trainerId);
            preparedStatement.setInt(2, memberId);
            preparedStatement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTrainerToAvailabilityTable(int trainerId) {
        try {
            formConnection();
            String insertQuery = "INSERT INTO trainer_availability (trainer_id) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, trainerId);
            preparedStatement.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setTrainerAvailability(int trainerId, ArrayList<String> selectedSlots) {
        try {
            formConnection();
            String updateQuery = "UPDATE trainer_availability SET ";
            for (String slot : selectedSlots) {
                String slotColumnName = mapSlotToColumnName(slot);
                if (slotColumnName != null) {
                    updateQuery += slotColumnName + " = TRUE, ";
                }
            }
            updateQuery = updateQuery.substring(0, updateQuery.length() - 2);

            updateQuery += " WHERE trainer_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, trainerId);
            preparedStatement.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private String mapSlotToColumnName(String slot) {
        switch (slot) {
            case "8:00-9:00":
                return "h_8_9";
            case "9:00-10:00":
                return "h_9_10";
            case "10:00-11:00":
                return "h_10_11";
            case "11:00-12:00":
                return "h_11_12";
            case "12:00-13:00":
                return "h_12_13";
            case "13:00-14:00":
                return "h_13_14";
            case "14:00-15:00":
                return "h_14_15";
            case "15:00-16:00":
                return "h_15_16";
            case "16:00-17:00":
                return "h_16_17";
            case "17:00-18:00":
                return "h_17_18";
            default:
                return null;
        }
    }



    public boolean bookTrainerTime(int trainerId, String timeSlot) {
        boolean success = false;
        try {
            formConnection();
            String updateQuery = "UPDATE trainer_availability SET " + mapSlotToColumnName(timeSlot) + " = FALSE WHERE trainer_id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(1, trainerId);
            updateStatement.executeUpdate();
            String addBookedTimeQuery = "UPDATE memberInfo SET booked_time = ? WHERE trainer_id = ?";
            PreparedStatement addBookedTimeStatement = connection.prepareStatement(addBookedTimeQuery);
            addBookedTimeStatement.setString(1, timeSlot);
            addBookedTimeStatement.setInt(2, trainerId);
            addBookedTimeStatement.executeUpdate();

            success = true;

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }




    public boolean bookClassTime(String className, String timeSlot) {
        boolean success = false;
        try {
            formConnection();
            String checkAvailabilityQuery = "SELECT " + mapSlotToColumnName(timeSlot) + " FROM classes WHERE class_name = ?";
            PreparedStatement checkAvailabilityStatement = connection.prepareStatement(checkAvailabilityQuery);
            checkAvailabilityStatement.setString(1, className);
            ResultSet resultSet = checkAvailabilityStatement.executeQuery();
            if (resultSet.next()) {
                boolean isAvailable = resultSet.getBoolean(1);
                if (isAvailable) {
                    String updateQuery = "UPDATE classes SET " + mapSlotToColumnName(timeSlot) + " = FALSE WHERE class_name = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                    updateStatement.setString(1, className);
                    updateStatement.executeUpdate();

                    success = true;
                } else {
                    System.out.println("The selected time slot is already booked.");
                }
            } else {
                System.out.println("Class not found: " + className);
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }


    public ArrayList<String> getAvailableTimeSlotsForTrainer(int trainerId) {
        ArrayList<String> availableTimeSlots = new ArrayList<>();

        try {
            formConnection();
            String selectQuery = "SELECT h_8_9, h_9_10, h_10_11, h_11_12, h_12_13, h_13_14, h_14_15, h_15_16, h_16_17, h_17_18 FROM trainer_availability WHERE trainer_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, trainerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                for (int i = 8; i <= 17; i++) {
                    String slotColumnName = "h_" + i + "_" + (i + 1);
                    boolean isAvailable = resultSet.getBoolean(slotColumnName.toLowerCase());
                    if (isAvailable) {
                        String timeSlot = i + ":00-" + (i + 1) + ":00";
                        availableTimeSlots.add(timeSlot);
                    }
                }
            } else {
                System.out.println("Trainer ID not found: " + trainerId);
            }

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableTimeSlots;
    }

    public ArrayList<String> getAvailableTimeSlotsForClass() {
        ArrayList<String> availableTimeSlots = new ArrayList<>();

        try {
            formConnection();
            String selectQuery = "SELECT h_8_9, h_9_10, h_10_11, h_11_12, h_12_13, h_13_14, h_14_15, h_15_16, h_16_17, h_17_18 FROM classes";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                for (int i = 8; i <= 17; i++) {
                    String slotColumnName = "h_" + i + "_" + (i + 1);
                    boolean isAvailable = resultSet.getBoolean(slotColumnName.toLowerCase());
                    if (isAvailable) {
                        String timeSlot = i + ":00-" + (i + 1) + ":00";
                        availableTimeSlots.add(timeSlot);
                    }
                }
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableTimeSlots;
    }

    public static void setClass() {
        try (Connection connection = DriverManager.getConnection(url, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String[] classNames = {"Yoga", "Calisthenics", "Weight Lifting", "Cardio"};
            for (String className : classNames) {
                String checkIfExistsQuery = "SELECT COUNT(*) FROM classes WHERE class_name = '" + className + "'";
                ResultSet resultSet = statement.executeQuery(checkIfExistsQuery);
                resultSet.next();
                int count = resultSet.getInt(1);
                if (count == 0) {
                    String updateAvailabilityQuery = "INSERT INTO classes (class_name, h_8_9, h_9_10, h_10_11, h_11_12, h_12_13, h_13_14, h_14_15, h_15_16, h_16_17, h_17_18) " +
                            "VALUES ('" + className + "', TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE)";
                    statement.executeUpdate(updateAvailabilityQuery);
                }
            }

            System.out.println("Classes and availability set successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setEquipment() {

        try (Connection connection = DriverManager.getConnection(url, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String[] defaultEquipment = {"HS Chest Press", "Smith Machine", "Pec Dec", "Leg Extensions",
                    "Hamstring Curls", "Shoulder Press", "Cables", "LatPullDown", "Seated Cable Rows",
                    "Treadmill", "Bicycle", "StairMaster", "Leg Press"};
            for (String equipment : defaultEquipment) {
                String checkIfExistsQuery = "SELECT COUNT(*) FROM equipment WHERE name = '" + equipment + "'";
                var resultSet = statement.executeQuery(checkIfExistsQuery);
                resultSet.next();
                int count = resultSet.getInt(1);
                if (count == 0) {
                    String insertEquipmentQuery = "INSERT INTO equipment (name) VALUES ('" + equipment + "')";
                    statement.executeUpdate(insertEquipmentQuery);
                    System.out.println("Added equipment: " + equipment);
                } else {
                    System.out.println("Equipment already exists: " + equipment);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateClassSchedule(String className, ArrayList<String> timeSlots) {
        try {
            formConnection();
            StringBuilder updateQuery = new StringBuilder("UPDATE classes SET ");
            for (String timeSlot : timeSlots) {
                String[] times = timeSlot.split("-");
                String startTime = times[0].trim().replace(":", "_");
                String endTime = times[1].trim().replace(":", "_");
                String[] x = startTime.split("_");
                String[] y = endTime.split("_");
                String startColumnName = "h_" + x[0] + "_" + y[0];
                updateQuery.append(startColumnName).append(" = FALSE, ");
            }

            updateQuery.setLength(updateQuery.length() - 2);
            updateQuery.append(" WHERE class_name = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery.toString());
            preparedStatement.setString(1, className);
            preparedStatement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    protected ArrayList<String> getTrainersById(int trainerId) {
        ArrayList<String> trainers = new ArrayList<>();
        try {
            formConnection();
            String selectQuery = "SELECT a.first_name, a.last_name " +
                    "FROM memberInfo m " +
                    "INNER JOIN accounts a ON m.id = a.id " +
                    "WHERE m.trainer_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, trainerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String fullName = firstName + " " + lastName;
                trainers.add(fullName);
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainers;
    }

    public void setEquipmentStatus(String name, String status) {
        try (Connection connection = DriverManager.getConnection(url, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String updateStatusQuery = "UPDATE equipment SET status = '" + status + "' WHERE name = '" + name + "'";
            int rowsAffected = statement.executeUpdate(updateStatusQuery);

            if (rowsAffected > 0) {
                System.out.println("Status updated for equipment: " + name);
            } else {
                System.out.println("Equipment not found: " + name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getStatus(String equipment) {
        String status = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(url, USER, PASSWORD);
            String query = "SELECT status FROM equipment WHERE name = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, equipment);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                status = resultSet.getString("status");
            } else {

                status = "Equipment not found";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return status;
    }

    public void bookRooms(int roomNum, ArrayList<String> times) {
        try (Connection conn = DriverManager.getConnection(url, USER, PASSWORD)) {
            for (String time : times) {
                String columnName = getTimeColumn(time);
                if (columnName != null) {

                    String updateQuery = "UPDATE rooms SET " + columnName + " = true WHERE room_number = ?";

                    try (PreparedStatement statement = conn.prepareStatement(updateQuery)) {
                        statement.setInt(1, roomNum);
                        statement.executeUpdate();
                        System.out.println("Room " + roomNum + " booked successfully for " + time);
                    }
                } else {
                    System.out.println("Invalid time slot: " + time);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }
    }

    public void payBill(int id) {
        try {
            formConnection();
            String updateQuery = "UPDATE memberInfo SET bill_to_pay = FALSE WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, id);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Bill paid successfully for member with ID: " + id);
            } else {
                System.out.println("No member found with ID: " + id);
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getBill(int id) {
        boolean billToPay = false;
        try {
            formConnection();
            String selectQuery = "SELECT bill_to_pay FROM memberInfo WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                billToPay = resultSet.getBoolean("bill_to_pay");
                return billToPay;
            } else {
                System.out.println("No member found with ID: " + id);
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billToPay;
    }

    public void createBill(int id) {
        try {
            formConnection();
            String updateQuery = "UPDATE memberInfo SET bill_to_pay = TRUE WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, id);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Bill created successfully for member with ID: " + id);
            } else {
                System.out.println("No member found with ID: " + id);
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getStaffRoomTimes(int id) {
        ArrayList<String> staffRoomTimes = new ArrayList<>();
        try {
            formConnection();
            String selectQuery = "SELECT availability_date, " +
                    "CONCAT(" +
                    "CASE WHEN h_8_9 THEN '8-9, ' ELSE '' END, " +
                    "CASE WHEN h_9_10 THEN '9-10, ' ELSE '' END, " +
                    "CASE WHEN h_10_11 THEN '10-11, ' ELSE '' END, " +
                    "CASE WHEN h_11_12 THEN '11-12, ' ELSE '' END, " +
                    "CASE WHEN h_12_13 THEN '12-13, ' ELSE '' END, " +
                    "CASE WHEN h_13_14 THEN '13-14, ' ELSE '' END, " +
                    "CASE WHEN h_14_15 THEN '14-15, ' ELSE '' END, " +
                    "CASE WHEN h_15_16 THEN '15-16, ' ELSE '' END, " +
                    "CASE WHEN h_16_17 THEN '16-17, ' ELSE '' END, " +
                    "CASE WHEN h_17_18 THEN '17-18, ' ELSE '' END) AS time " +
                    "FROM trainer_availability";

            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                String availabilityDate = resultSet.getString("availability_date");
                String time = resultSet.getString("time");

                if (time.endsWith(", ")) {
                    time = time.substring(0, time.length() - 2);
                }

                staffRoomTimes.add(availabilityDate + " " + time);
            }

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffRoomTimes;
    }

    public ArrayList<Integer> getRooms() {
        ArrayList<Integer> roomsWithTrueValue = new ArrayList<>();
        try {
            formConnection();
            String selectQuery = "SELECT DISTINCT room_number FROM rooms WHERE " +
                    "h_8_9 OR h_9_10 OR h_10_11 OR h_11_12 OR h_12_13 OR " +
                    "h_13_14 OR h_14_15 OR h_15_16 OR h_16_17 OR h_17_18";

            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                int roomNumber = resultSet.getInt("room_number");
                roomsWithTrueValue.add(roomNumber);
            }

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomsWithTrueValue;
    }

    public ArrayList<Integer> getRoomV2() {
        ArrayList<Integer> availableRooms = new ArrayList<>();
        try {
            formConnection();
            String selectQuery = "SELECT DISTINCT room_number FROM rooms WHERE " +
                    "NOT (h_8_9 AND h_9_10 AND h_10_11 AND h_11_12 AND h_12_13 AND " +
                    "h_13_14 AND h_14_15 AND h_15_16 AND h_16_17 AND h_17_18)";

            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                int roomNumber = resultSet.getInt("room_number");
                availableRooms.add(roomNumber);
            }

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableRooms;
    }


    public ArrayList<String> getTimeRoomNum(int roomNum) {
        ArrayList<String> timesWithTrueValue = new ArrayList<>();
        try {
            formConnection();
            String selectQuery = "SELECT * FROM rooms WHERE room_number = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setInt(1, roomNum);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getBoolean("h_8_9")) timesWithTrueValue.add("8-9");
                if (resultSet.getBoolean("h_9_10")) timesWithTrueValue.add("9-10");
                if (resultSet.getBoolean("h_10_11")) timesWithTrueValue.add("10-11");
                if (resultSet.getBoolean("h_11_12")) timesWithTrueValue.add("11-12");
                if (resultSet.getBoolean("h_12_13")) timesWithTrueValue.add("12-13");
                if (resultSet.getBoolean("h_13_14")) timesWithTrueValue.add("13-14");
                if (resultSet.getBoolean("h_14_15")) timesWithTrueValue.add("14-15");
                if (resultSet.getBoolean("h_15_16")) timesWithTrueValue.add("15-16");
                if (resultSet.getBoolean("h_16_17")) timesWithTrueValue.add("16-17");
                if (resultSet.getBoolean("h_17_18")) timesWithTrueValue.add("17-18");
            }

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timesWithTrueValue;
    }

    public ArrayList<String> getAvailableTimeSlots(int roomNum) {
        ArrayList<String> availableTimeSlots = new ArrayList<>();
        try {
            formConnection();
            String selectQuery = "SELECT * FROM rooms WHERE room_number = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setInt(1, roomNum);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                if (!resultSet.getBoolean("h_8_9")) availableTimeSlots.add("8-9");
                if (!resultSet.getBoolean("h_9_10")) availableTimeSlots.add("9-10");
                if (!resultSet.getBoolean("h_10_11")) availableTimeSlots.add("10-11");
                if (!resultSet.getBoolean("h_11_12")) availableTimeSlots.add("11-12");
                if (!resultSet.getBoolean("h_12_13")) availableTimeSlots.add("12-13");
                if (!resultSet.getBoolean("h_13_14")) availableTimeSlots.add("13-14");
                if (!resultSet.getBoolean("h_14_15")) availableTimeSlots.add("14-15");
                if (!resultSet.getBoolean("h_15_16")) availableTimeSlots.add("15-16");
                if (!resultSet.getBoolean("h_16_17")) availableTimeSlots.add("16-17");
                if (!resultSet.getBoolean("h_17_18")) availableTimeSlots.add("17-18");
            }

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableTimeSlots;
    }
    public ArrayList<String> getTimeSlotsWithTrueValue() {
        ArrayList<String> timeSlotsWithTrueValue = new ArrayList<>();
        try {
            formConnection();
            String selectQuery = "SELECT DISTINCT " +
                    "CONCAT(" +
                    "CASE WHEN h_8_9 THEN '8-9, ' ELSE '' END, " +
                    "CASE WHEN h_9_10 THEN '9-10, ' ELSE '' END, " +
                    "CASE WHEN h_10_11 THEN '10-11, ' ELSE '' END, " +
                    "CASE WHEN h_11_12 THEN '11-12, ' ELSE '' END, " +
                    "CASE WHEN h_12_13 THEN '12-13, ' ELSE '' END, " +
                    "CASE WHEN h_13_14 THEN '13-14, ' ELSE '' END, " +
                    "CASE WHEN h_14_15 THEN '14-15, ' ELSE '' END, " +
                    "CASE WHEN h_15_16 THEN '15-16, ' ELSE '' END, " +
                    "CASE WHEN h_16_17 THEN '16-17, ' ELSE '' END, " +
                    "CASE WHEN h_17_18 THEN '17-18, ' ELSE '' END) AS time " +
                    "FROM trainer_availability " +
                    "WHERE " +
                    "h_8_9 OR h_9_10 OR h_10_11 OR h_11_12 OR h_12_13 OR " +
                    "h_13_14 OR h_14_15 OR h_15_16 OR h_16_17 OR h_17_18";

            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                String timeSlot = resultSet.getString("time");
                if (timeSlot.endsWith(", ")) {
                    timeSlot = timeSlot.substring(0, timeSlot.length() - 2);
                }

                timeSlotsWithTrueValue.add(timeSlot);
            }

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timeSlotsWithTrueValue;
    }


    public void addOrUpdateMemberRecord(int memberId, String workoutName, String record) throws SQLException {

        String queryCheck = "SELECT * FROM member_records WHERE id = ? AND workout_name = ?";
        formConnection();
        try (PreparedStatement checkStatement = connection.prepareStatement(queryCheck)) {
            checkStatement.setInt(1, memberId);
            checkStatement.setString(2, workoutName);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                String updateQuery = "UPDATE member_records SET record = ? WHERE id = ? AND workout_name = ?";
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setString(1, record);
                    updateStatement.setInt(2, memberId);
                    updateStatement.setString(3, workoutName);
                    updateStatement.executeUpdate();
                }
            } else {
                String insertQuery = "INSERT INTO member_records (id, workout_name, record) VALUES (?, ?, ?)";
                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    insertStatement.setInt(1, memberId);
                    insertStatement.setString(2, workoutName);
                    insertStatement.setString(3, record);
                    insertStatement.executeUpdate();
                }
            }
        }
    }
    public HashMap<String, String> extractMemberRecords(int memberId) throws SQLException {
        HashMap<String, String> memberRecords = new HashMap<>();
        String query = "SELECT workout_name, record FROM member_records WHERE id = ?";
        formConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, memberId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String workoutName = resultSet.getString("workout_name");
                String record = resultSet.getString("record");

                memberRecords.put(workoutName, record);
            }
        }

        return memberRecords;
    }
    public ArrayList<String> getMemberDetails(int memberId) {
        ArrayList<String> memberDetails = new ArrayList<>();
        try {
            formConnection();
            String selectQuery = "SELECT body_weight, height, gender, age, goal_weight FROM memberInfo WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, memberId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double bodyWeight = resultSet.getDouble("body_weight");
                double height = resultSet.getDouble("height");
                String gender = resultSet.getString("gender");
                int age = resultSet.getInt("age");
                double goalWeight = resultSet.getDouble("goal_weight");
                memberDetails.add(String.valueOf(bodyWeight));
                memberDetails.add(String.valueOf(height));
                memberDetails.add(gender);
                memberDetails.add(String.valueOf(age));
                memberDetails.add(String.valueOf(goalWeight));
            } else {
                System.out.println("Member with ID " + memberId + " not found.");
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memberDetails;
    }

    public void unbookRoom(int roomNum, ArrayList<String> times) {
        try {
            formConnection();

            // Update the room schedule
            String updateRoomQuery = "UPDATE rooms SET ";
            for (int i = 0; i < times.size(); i++) {
                if (i > 0) {
                    updateRoomQuery += ", ";
                }
                String[] hours = times.get(i).split("-");
                String startHour = hours[0];
                String endHour = hours[1];
                updateRoomQuery += "h_" + startHour + "_" + endHour + " = FALSE";
            }
            updateRoomQuery += " WHERE room_number = ?";
            PreparedStatement updateRoomStatement = connection.prepareStatement(updateRoomQuery);
            updateRoomStatement.setInt(1, roomNum);
            updateRoomStatement.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editMemberDetails(int memberId, double bodyWeight, double height, String gender, int age, double goalWeight) {
        try {
            formConnection();
            String updateQuery = "UPDATE memberInfo SET body_weight = ?, height = ?, gender = ?, age = ?, goal_weight = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setDouble(1, bodyWeight);
            preparedStatement.setDouble(2, height);
            preparedStatement.setString(3, gender);
            preparedStatement.setInt(4, age);
            preparedStatement.setDouble(5, goalWeight);
            preparedStatement.setInt(6, memberId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Member details updated successfully.");
            } else {
                System.out.println("Failed to update member details.");
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public double calculateOverallCalories(int memberId) {
        double caloriesConsumed = 0;
        double caloriesBurnt = 0;

        try {
            formConnection();

            String selectCaloriesConsumedQuery = "SELECT SUM(calories) AS total_calories FROM foodInfo WHERE id = ?";
            PreparedStatement caloriesConsumedStatement = connection.prepareStatement(selectCaloriesConsumedQuery);
            caloriesConsumedStatement.setInt(1, memberId);
            ResultSet caloriesConsumedResult = caloriesConsumedStatement.executeQuery();
            if (caloriesConsumedResult.next()) {
                caloriesConsumed = caloriesConsumedResult.getDouble("total_calories");
            }

            String selectCaloriesBurntQuery = "SELECT SUM(calories_burnt) AS total_calories FROM cardio WHERE id = ? UNION ALL SELECT SUM(calories_burnt) AS total_calories FROM exercises WHERE id = ?";
            PreparedStatement caloriesBurntStatement = connection.prepareStatement(selectCaloriesBurntQuery);
            caloriesBurntStatement.setInt(1, memberId);
            caloriesBurntStatement.setInt(2, memberId);
            ResultSet caloriesBurntResult = caloriesBurntStatement.executeQuery();
            while (caloriesBurntResult.next()) {
                caloriesBurnt += caloriesBurntResult.getDouble("total_calories");
            }

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return caloriesConsumed - caloriesBurnt;
    }

    public int calculateOverallSteps(int memberId) {
        int totalSteps = 0;

        try {
            formConnection();
            String selectTotalStepsQuery = "SELECT SUM(steps) AS total_steps FROM cardio WHERE id = ?";
            PreparedStatement totalStepsStatement = connection.prepareStatement(selectTotalStepsQuery);
            totalStepsStatement.setInt(1, memberId);
            ResultSet totalStepsResult = totalStepsStatement.executeQuery();
            if (totalStepsResult.next()) {
                totalSteps = totalStepsResult.getInt("total_steps");
            }

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalSteps;
    }
    public ArrayList<String> getFoodConsumed(int memberId) {
        ArrayList<String> consumedFoods = new ArrayList<>();
        String query = "SELECT food_name, calories, protein, carbs, sugar, fats FROM foodInfo WHERE id = ?";


        formConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                String foodName = rs.getString("food_name");
                int calories = rs.getInt("calories");
                double protein = rs.getDouble("protein");
                double carbs = rs.getDouble("carbs");
                double sugar = rs.getDouble("sugar");
                double fats = rs.getDouble("fats");

                String foodInfo = foodName + ":  Calories: " + calories + "  Protein: " + protein + "g  Carbs: " + carbs + "g  Sugar: " + sugar + "g  Fats: " + fats + "g";

                consumedFoods.add(foodInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consumedFoods;
    }
    public double[] getTotalMacros( int memberId) {
        double totalProtein = 0;
        int totalCalories = 0;
        double totalCarbs = 0;
        double totalSugar = 0;
        double totalFats = 0;

        String query = "SELECT protein, calories, carbs, sugar, fats FROM foodInfo WHERE id = ?";
        formConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                totalProtein += rs.getDouble("protein");
                totalCalories += rs.getInt("calories");
                totalCarbs += rs.getDouble("carbs");
                totalSugar += rs.getDouble("sugar");
                totalFats += rs.getDouble("fats");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new double[]{totalProtein, totalCalories, totalCarbs, totalSugar, totalFats};
    }
    public ArrayList<String> getAllWeightLiftingExercises(int memberId) {
        ArrayList<String> weightLiftingExercises = new ArrayList<>();
        String query = "SELECT * FROM exercises WHERE id = ?";
        formConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String exerciseName = rs.getString("workout_name");
                int reps = rs.getInt("reps");
                int sets = rs.getInt("sets");
                double caloriesBurnt = rs.getDouble("calories_burnt");

                String exerciseInfo = String.format("Exercise: %s, Reps: %d, Sets: %d, Calories Burnt: %.2f",
                        exerciseName, reps, sets, caloriesBurnt);
                weightLiftingExercises.add(exerciseInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return weightLiftingExercises;
    }
    public ArrayList<String> getAllCardioExercises(int memberId) {
        ArrayList<String> cardioExercises = new ArrayList<>();
        String query = "SELECT * FROM cardio WHERE id = ?";
        formConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String exerciseName = rs.getString("exercise_name");
                int durationMinutes = rs.getInt("duration_minutes");
                int steps = rs.getInt("steps");
                double distance = rs.getDouble("distance");
                double caloriesBurnt = rs.getDouble("calories_burnt");

                String exerciseInfo = String.format("Exercise: %s, Duration: %d mins, Steps: %d, Distance: %.2f km, Calories Burnt: %.2f",
                        exerciseName, durationMinutes, steps, distance, caloriesBurnt);
                cardioExercises.add(exerciseInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cardioExercises;
    }
    public void clearFoodInfoForMember(int memberId) {
        try {
            formConnection();
            String deleteQuery = "DELETE FROM foodInfo WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, memberId);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Food information cleared for member with id: " + memberId);
            } else {
                System.out.println("No food information found for member with id: " + memberId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            closeConnection();
        }
    }
    public void addBookedWorkoutClass(int memberId, String className, String classTime) {
        try {
            formConnection();
            String insertQuery = "INSERT INTO bookedClasses (member_id, class_name, class_time) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, memberId);
            preparedStatement.setString(2, className);
            preparedStatement.setString(3, classTime);
            preparedStatement.executeUpdate();
            closeConnection();
            System.out.println("Workout class booked successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getAllAvailableWorkoutClasses() {
        ArrayList<String> availableWorkoutClasses = new ArrayList<>();
        try {
            formConnection();

            String selectQuery = "SELECT class_name FROM workout_classes WHERE " +
                    "room_id IS NOT NULL AND (" +
                    "h_8_9 = FALSE OR h_9_10 = FALSE OR h_10_11 = FALSE OR h_11_12 = FALSE OR " +
                    "h_12_13 = FALSE OR h_13_14 = FALSE OR h_14_15 = FALSE OR h_15_16 = FALSE OR " +
                    "h_16_17 = FALSE OR h_17_18 = FALSE)";

            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                String className = resultSet.getString("class_name");
                availableWorkoutClasses.add(className);
            }

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableWorkoutClasses;
    }


    public void scheduleWorkoutClass(String className, int roomNumber, ArrayList<String> times) {
        try {
            formConnection();
            for (String time : times) {
                String updateClassQuery = "INSERT INTO workout_classes (class_name, room_id, " + getTimeColumn(time) + ") VALUES (?, ?, TRUE) "
                        + "ON CONFLICT (class_name) DO UPDATE SET " + getTimeColumn(time) + " = TRUE, room_id = ?";
                PreparedStatement updateClassStatement = connection.prepareStatement(updateClassQuery);
                updateClassStatement.setString(1, className);
                updateClassStatement.setInt(2, roomNumber);
                updateClassStatement.setInt(3, roomNumber);
                updateClassStatement.executeUpdate();

                System.out.println("Workout class '" + className + "' scheduled successfully in room " + roomNumber + " at " + time);
            }

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void unscheduleWorkoutClass(int roomNum) {
        try {
            formConnection();

            // Construct the update query to set all time slots to FALSE and roomId to null for the given room number
            String updateClassQuery = "UPDATE workout_classes SET "
                    + "room_id = NULL, "
                    + "h_8_9 = FALSE, "
                    + "h_9_10 = FALSE, "
                    + "h_10_11 = FALSE, "
                    + "h_11_12 = FALSE, "
                    + "h_12_13 = FALSE, "
                    + "h_13_14 = FALSE, "
                    + "h_14_15 = FALSE, "
                    + "h_15_16 = FALSE, "
                    + "h_16_17 = FALSE, "
                    + "h_17_18 = FALSE "
                    + "WHERE room_id = ?";

            // Prepare and execute the update statement
            PreparedStatement updateClassStatement = connection.prepareStatement(updateClassQuery);
            updateClassStatement.setInt(1, roomNum);
            updateClassStatement.executeUpdate();

            System.out.println("Workout classes in room number " + roomNum + " unscheduled successfully.");

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    public ArrayList<String> getTrueTimes( String className) {
        ArrayList<String> trueTimes = new ArrayList<>();
        String query = "SELECT * FROM workout_classes WHERE class_name = ?";

        try {
            formConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, className);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                for (int i = 8; i <= 17; i++) {
                    String columnName = "h_" + i + "_" + (i + 1);
                    System.out.println(resultSet.getBoolean(columnName));
                    if (resultSet.getBoolean(columnName)) {
                        trueTimes.add(columnName);
                        System.out.println(columnName);
                    }
                }
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return trueTimes;
    }
    public static String getTimeColumn(String time) {
        switch (time) {
            case "8-9":
            case "8:00-9:00":
                return "h_8_9";
            case "9-10":
            case "9:00-10:00":
                return "h_9_10";
            case "10-11":
            case "10:00-11:00":
                return "h_10_11";
            case "11-12":
            case "11:00-12:00":
                return "h_11_12";
            case "12-13":
            case "12:00-13:00":
                return "h_12_13";
            case "13-14":
            case "13:00-14:00":
                return "h_13_14";
            case "14-15":
            case "14:00-15:00":
                return "h_14_15";
            case "15-16":
            case "15:00-16:00":
                return "h_15_16";
            case "16-17":
            case "16:00-17:00":
                return "h_16_17";
            case "17-18":
            case "17:00-18:00":
                return "h_17_18";
            case "18-19":
            case "18:00-19:00":
                return "h_18_19";
            default:
                return null;
        }
    }

    public boolean unbookTrainer(int id) {
        try {
            formConnection();


            String getBookedInfoQuery = "SELECT trainer_id, booked_time FROM memberInfo WHERE id = ?";
            PreparedStatement getBookedInfoStatement = connection.prepareStatement(getBookedInfoQuery);
            getBookedInfoStatement.setInt(1, id);
            ResultSet resultSet = getBookedInfoStatement.executeQuery();
            String bookedTime = null;
            int trainerId = -1;
            if (resultSet.next()) {
                bookedTime = resultSet.getString("booked_time");
                trainerId = resultSet.getInt("trainer_id");
            }
            getBookedInfoStatement.close();

            if (bookedTime != null && trainerId != -1) {

                String memberInfoQuery = "UPDATE memberInfo SET trainer_id = NULL, booked_time = NULL WHERE id = ?";
                PreparedStatement memberInfoStatement = connection.prepareStatement(memberInfoQuery);
                memberInfoStatement.setInt(1, id);
                int rowsAffectedMemberInfo = memberInfoStatement.executeUpdate();
                memberInfoStatement.close();


                updateTrainerAvailability(trainerId, bookedTime);
                connection.close();


                return rowsAffectedMemberInfo > 0;
            } else {
                connection.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void updateTrainerAvailability(int trainerId, String bookedTime) {
        try {
            formConnection();
            String columnName = getTimeColumn(bookedTime);
            System.out.println(columnName);
            if (columnName != null) {
                String updateAvailabilityQuery = "UPDATE trainer_availability SET " + columnName + " = TRUE WHERE trainer_id = ?";
                PreparedStatement updateAvailabilityStatement = connection.prepareStatement(updateAvailabilityQuery);
                updateAvailabilityStatement.setInt(1, trainerId);
                updateAvailabilityStatement.executeUpdate();

                closeConnection();
                System.out.println("Trainer availability updated successfully for trainer with ID: " + trainerId);
            } else {
                System.out.println("Invalid booked time: " + bookedTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
