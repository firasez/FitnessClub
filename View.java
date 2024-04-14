import com.sun.management.GarbageCollectionNotificationInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.*;
import java.util.HashMap;


public class View extends JFrame {
    private JButton signUpButton, signInButton;
    private JTextField nameField, lastNameField, usernameField;
    private JPasswordField passwordField;
    static Controller c1;
    private JFrame signInFrame;
    private JFrame welcomeFrame;
    private JFrame staffWelcomePage;
    private String account_type;
    private boolean memberSignedIn;
    private boolean trainerSignedIn;
    private boolean staffSignedIn;
    private ArrayList<String> members;

    public View() {
        super("Fitness Club");
        usernameField = new JTextField(20);
        setName("Fitness Club Frame");

        signUpButton = new JButton("Sign Up");
        signInButton = new JButton("Sign In");

        JLabel promptLabel = new JLabel("Welcome to FitnessClub!");
        promptLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel createAccountLabel = new JLabel("Create an account:");

        JLabel alreadyMemberLabel = new JLabel("Already a member?");

        JPanel mainPanel = new JPanel(new GridLayout(4, 1));
        mainPanel.add(promptLabel);

        JPanel signUpPanel = new JPanel();
        signUpPanel.setLayout(new FlowLayout());
        signUpPanel.add(createAccountLabel);
        signUpPanel.add(signUpButton);
        mainPanel.add(signUpPanel);

        JPanel signInPanel = new JPanel();
        signInPanel.setLayout(new FlowLayout());
        signInPanel.add(alreadyMemberLabel);
        signInPanel.add(signInButton);
        mainPanel.add(signInPanel);

        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openSignUpScreen();
                dispose();
            }
        });

        signInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openSignInScreen();
                dispose();
            }
        });

        getContentPane().add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 250));
        pack();
        setVisible(true);
    }


    public void setController(Controller c2){
        this.c1 = c2;
    }
    private void openSignUpScreen() {

        this.dispose();
        JFrame signUpFrame = new JFrame("Sign Up");
        signUpFrame.setLayout(new GridLayout(7, 2));
        signUpFrame.add(new JLabel("Name:"));
        nameField = new JTextField(20);
        signUpFrame.add(nameField);
        signUpFrame.add(new JLabel("Last Name:"));
        lastNameField = new JTextField(20);
        signUpFrame.add(lastNameField);
        signUpFrame.add(new JLabel("Username:"));
        usernameField = new JTextField(20);
        signUpFrame.add(usernameField);
        signUpFrame.add(new JLabel("Password:"));
        passwordField = new JPasswordField(20);
        signUpFrame.add(passwordField);
        signUpFrame.add(new JLabel("What are you?:"));
        String[] categories = {"Trainer", "Member", "Staff"};
        JComboBox<String> categoryDropdown = new JComboBox<>(categories);
        signUpFrame.add(categoryDropdown);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c1.signUp(nameField.getText(),lastNameField.getText(),usernameField.getText(),passwordField.getPassword(),(String) categoryDropdown.getSelectedItem());

                if (categoryDropdown.getSelectedItem().equals("Member")) {
                    signUpFrame.dispose();
                    openMemberQuestionsScreen();

                } else if (categoryDropdown.getSelectedItem().equals("Trainer")) {
                    signUpFrame.dispose();
                    openTrainerWelcomePage(usernameField.getText());
                    c1.createTrainer(Model.getIdByUsername(usernameField.getText()));
                } else if (categoryDropdown.getSelectedItem().equals("Staff")) {

                    signUpFrame.dispose();
                    openStaffWelcomePage(usernameField.getText());
                }
            }

        });
        signUpFrame.add(signUpButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signUpFrame.dispose();
                new View();
            }
        });
        signUpFrame.add(backButton);

        signUpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signUpFrame.pack();
        signUpFrame.setVisible(true);
    }

    protected void openStaffWelcomePage(String name) {
        
        staffWelcomePage = new JFrame("Welcome " + name);
        staffWelcomePage.setLayout(new BorderLayout());

        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel welcomeLabel = new JLabel("Welcome back " + name + "!");
        welcomePanel.add(welcomeLabel);

        JPanel clockPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel clockLabel = new JLabel("You are currently on the clock");
        clockPanel.add(clockLabel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));

        JButton bookButton = new JButton("Book a Room");
        JButton monitorButton = new JButton("Monitor Equipment");
        JButton unbookButton = new JButton("Unbook a Room");
        JButton paymentButton = new JButton("Member Payment");
        JButton logoutButton = new JButton("Log Out");


        buttonPanel.add(bookButton);
        buttonPanel.add(monitorButton);
        buttonPanel.add(unbookButton);
        buttonPanel.add(paymentButton);
        buttonPanel.add(logoutButton);


        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                staffWelcomePage.dispose();
                openBookRoomPage(name);

            }
        });

        monitorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                staffWelcomePage.dispose();
                openMonitorEquipmentWindow(name);
            }
        });

        unbookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                staffWelcomePage.dispose();
                openUnbookRoomPage(name);
            }
        });

        paymentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openMemberPayment();
            }
        });

        logoutButton.addActionListener(e -> {
            new View();
            staffWelcomePage.dispose();
        });


        staffWelcomePage.add(welcomePanel, BorderLayout.NORTH);
        staffWelcomePage.add(clockPanel, BorderLayout.CENTER);
        staffWelcomePage.add(buttonPanel, BorderLayout.SOUTH);

        staffWelcomePage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        staffWelcomePage.pack();
        staffWelcomePage.setLocationRelativeTo(null);
        staffWelcomePage.setVisible(true);
    }

    public static void openMemberPayment() {
        JFrame frame = new JFrame("Member Payment");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ArrayList<String> members = c1.getMembers();

        JList<String> memberList = new JList<>(members.toArray(new String[0]));

        memberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        memberList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedMember = memberList.getSelectedValue();
                int option = JOptionPane.showConfirmDialog(frame, "Do you want to make " + selectedMember + " pay a bill?", "Payment Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.out.println(selectedMember + " is paying a bill.");
                    c1.createBill(Model.getIdByUsername(selectedMember));
                }
            }
        });

        frame.add(new JScrollPane(memberList), BorderLayout.CENTER);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public  void openMonitorEquipmentWindow(String username) {
        JFrame equipmentFrame = new JFrame("Equipment Monitor");
        equipmentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        equipmentFrame.setSize(650, 700);
        equipmentFrame.setLayout(new GridLayout(0, 2, 10, 10));


        String[] equipmentNames = {"HS Chest Press", "Smith Machine", "Pec Dec", "Leg Extensions", "Hamstring Curls","Shoulder Press",
                "Cables", "LatPullDown","Seated Cable Rows","Treadmill", "Bicycle", "StairMaster", "Leg Press"};

        JButton backButton = new JButton("Back");



        for (String equipmentName : equipmentNames) {
            JLabel nameLabel = new JLabel(equipmentName);
            equipmentFrame.add(nameLabel);
            JToggleButton statusButton = new JToggleButton(c1.getEquipmentStatus(nameLabel.getText()));
            statusButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (statusButton.isSelected()) {
                        statusButton.setText("Out of Order");
                        c1.setEquipmentStatus(nameLabel.getText(), statusButton.getText());
                    } else {
                        statusButton.setText("Working");
                        c1.setEquipmentStatus(nameLabel.getText(), statusButton.getText());
                    }
                }
            });

            equipmentFrame.add(statusButton);

        }
        equipmentFrame.add(new JLabel());
        equipmentFrame.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                equipmentFrame.dispose();
                openStaffWelcomePage(username);

            }
        });

        equipmentFrame.setVisible(true);
    }



    private void openMemberQuestionsScreen() {

        this.dispose();


        JFrame signUpFrame = new JFrame("Member Questions");
        signUpFrame.setLayout(new BorderLayout());


        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel welcomeLabel = new JLabel("Welcome to FitnessClub " + nameField.getText() + "! Please answer the following questions below!");
        topPanel.add(welcomeLabel);

        JPanel questionsPanel = new JPanel();
        questionsPanel.setLayout(new GridLayout(5, 2));

        questionsPanel.add(new JLabel("What's your body weight? (kg)"));
        JTextField weightField = new JTextField(20);
        questionsPanel.add(weightField);
        questionsPanel.add(new JLabel("What's your height? (cm)"));
        JTextField heightField = new JTextField(20);
        questionsPanel.add(heightField);
        questionsPanel.add(new JLabel("What's your goal weight? (kg)"));
        JTextField goalWeightField = new JTextField(20);
        questionsPanel.add(goalWeightField);
        questionsPanel.add(new JLabel("How old are you?"));
        JTextField ageField = new JTextField(20);
        questionsPanel.add(ageField);

        questionsPanel.add(new JLabel("What is your gender?"));
        String[] genders = {"Male", "Female"};
        JComboBox<String> genderDropdown = new JComboBox<>(genders);
        questionsPanel.add(genderDropdown);


        signUpFrame.add(topPanel, BorderLayout.NORTH);
        signUpFrame.add(questionsPanel, BorderLayout.CENTER);


        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                double weight = Double.parseDouble(weightField.getText());
                double height = Double.parseDouble(heightField.getText());
                double goalWeight = Double.parseDouble(goalWeightField.getText());
                int age = Integer.parseInt(ageField.getText());
                String gender = (String) genderDropdown.getSelectedItem();
                c1.submitInfo(usernameField.getText(),Model.getIdByUsername(usernameField.getText()), weight, height, goalWeight, age, gender);

                signUpFrame.dispose();

            }
        });
        signUpFrame.add(submitButton, BorderLayout.SOUTH);

        signUpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signUpFrame.pack();
        signUpFrame.setVisible(true);
    }

    private void openSignInScreen() {

        this.dispose();


        signInFrame = new JFrame("Sign In");
        signInFrame.setLayout(new GridLayout(4, 2));


        signInFrame.add(new JLabel("Username:"));
        usernameField = new JTextField(20);
        signInFrame.add(usernameField);
        signInFrame.add(new JLabel("Password:"));
        passwordField = new JPasswordField(20);
        signInFrame.add(passwordField);


        JButton signInButton = new JButton("Sign In");
        signInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signInFrame.revalidate();
                c1.signIn(usernameField.getText(), passwordField.getPassword());
                signInFrame.dispose();
            }
        });
        signInFrame.add(signInButton);


        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signInFrame.dispose();
                new View();
            }
        });
        signInFrame.add(backButton);

        signInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signInFrame.pack();
        signInFrame.setVisible(true);
    }
    protected void openWelcomePage(String username) {

        welcomeFrame = new JFrame("Fitness Club");
        welcomeFrame.setLayout(new BorderLayout());
        welcomeFrame.setPreferredSize(new Dimension(1500,200));

        double dailyCalories = c1.calculateOverallCalories(Model.getIdByUsername(username));
        int dailySteps = c1.calculateOverallSteps(Model.getIdByUsername(username));


        JPanel welcomePanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome " + username + "!");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);


        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel infoLabel = new JLabel("Daily Calories: " + dailyCalories + "   Daily Steps: " + dailySteps);
        infoPanel.add(infoLabel);


        welcomeFrame.add(welcomePanel, BorderLayout.NORTH);
        welcomeFrame.add(infoPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 6, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton logFoodButton = new JButton("Log Food");
        JButton settingsButton = new JButton("Settings");
        JButton exercisesButton = new JButton("Exercises");
        JButton trainersButton = new JButton("Book Trainers");
        JButton payButton = new JButton("Pay a Bill");
        JButton fitnessAchievementButton = new JButton("Fitness Achievement");
        JButton logOutButton = new JButton("Log Out");
        JButton viewStatsButton = new JButton("View Stats");

        Dimension buttonSize = new Dimension(100, 30);
        logFoodButton.setPreferredSize(buttonSize);
        settingsButton.setPreferredSize(buttonSize);
        exercisesButton.setPreferredSize(buttonSize);
        trainersButton.setPreferredSize(buttonSize);
        payButton.setPreferredSize(buttonSize);
        logOutButton.setPreferredSize(buttonSize);
        fitnessAchievementButton.setPreferredSize(buttonSize);
        viewStatsButton.setPreferredSize(buttonSize);
        JButton bookClassButton = new JButton("Book Class");
        bookClassButton.setPreferredSize(buttonSize);

        logFoodButton.addActionListener(e -> {
            openLogFoodPage(username);

        });

        settingsButton.addActionListener(e -> {
            openSettingsPage();
            welcomeFrame.dispose();
        });

        payButton.addActionListener(e -> {
            payABill(username);
        });

        trainersButton.addActionListener(e -> {

            openTrainersList(username);
            welcomeFrame.dispose();

        });


        exercisesButton.addActionListener(e -> {
            welcomeFrame.dispose();
            openExercisepage(username);
        });

        logOutButton.addActionListener(e->{
            welcomeFrame.dispose();
            new View();

        });
        fitnessAchievementButton.addActionListener(e -> {
            try {
                openFitnessAchievementPage(c1.extractAchievements(Model.getIdByUsername(username)));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        viewStatsButton.addActionListener(e -> {
            openStatsPage(c1.memberDetails(Model.getIdByUsername(username)));
        });
        bookClassButton.addActionListener(e -> {
            openBookClassGUI(username);
        });
        buttonPanel.add(logFoodButton);

        buttonPanel.add(settingsButton);

        buttonPanel.add(exercisesButton);

        buttonPanel.add(trainersButton);

        buttonPanel.add(payButton);


        buttonPanel.add(fitnessAchievementButton);

        buttonPanel.add(viewStatsButton);

        buttonPanel.add(bookClassButton);

        buttonPanel.add(logOutButton);

        welcomeFrame.add(buttonPanel, BorderLayout.SOUTH);

        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.pack();
        welcomeFrame.setLocationRelativeTo(null);
        welcomeFrame.setVisible(true);
    }
    private void payABill(String name) {
        boolean hasBill = c1.getBill(Model.getIdByUsername(name));
        if (hasBill) {
            c1.payBill(Model.getIdByUsername(name));
            JOptionPane.showMessageDialog(null, "You have paid the bill! Thank you");
        } else{
            System.out.println("i am"+name);
            JOptionPane.showMessageDialog(null, "No bill to pay.");

        }
    }
    protected void openTrainerWelcomePage(String username) {

        JFrame frame = new JFrame("Welcome " + username);
        frame.setLayout(new BorderLayout());


        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel welcomeLabel = new JLabel("Welcome " + username + "!");
        welcomePanel.add(welcomeLabel);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));


        JButton scheduleButton = new JButton("Your Schedule");
        JButton membersButton = new JButton("Your Members");
        JButton logoutButton = new JButton("Log Out");

        buttonPanel.add(scheduleButton);
        buttonPanel.add(membersButton);
        buttonPanel.add(logoutButton);


        scheduleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                openAvailabilityPage(username);
            }
        });

        membersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                openMembersList(username);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new View();
                frame.dispose();
            }
        });


        frame.add(welcomePanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);


        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public void openLogFoodPage(String username) {

        JFrame frame = new JFrame("Log Food Page");


        JLabel nameLabel = new JLabel("Enter Name of Food:");
        JTextField nameField = new JTextField(20);

        JLabel caloriesLabel = new JLabel("Enter Calories amount:");
        JTextField caloriesField = new JTextField(20);

        JLabel proteinLabel = new JLabel("Enter Protein amount (g):");
        JTextField proteinField = new JTextField(20);

        JLabel carbsLabel = new JLabel("Enter Carbs amount (g):");
        JTextField carbsField = new JTextField(20);

        JLabel sugarLabel = new JLabel("Enter Sugar amount (g):");
        JTextField sugarField = new JTextField(20);

        JLabel fatsLabel = new JLabel("Enter fats amount (g):");
        JTextField fatsField = new JTextField(20);


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(caloriesLabel);
        panel.add(caloriesField);
        panel.add(proteinLabel);
        panel.add(proteinField);
        panel.add(carbsLabel);
        panel.add(carbsField);
        panel.add(sugarLabel);
        panel.add(sugarField);
        panel.add(fatsLabel);
        panel.add(fatsField);

        JButton enterButton = new JButton("Enter");
        panel.add(enterButton);


        JButton viewAllButton = new JButton("View All Foods");
        panel.add(viewAllButton);


        JButton clearButton = new JButton("Clear");
        panel.add(clearButton);


        enterButton.addActionListener(e -> {

            String foodName = nameField.getText();
            String cals = caloriesField.getText();
            double protein = Double.parseDouble(proteinField.getText());
            double carbs = Double.parseDouble(carbsField.getText());
            double sugar = Double.parseDouble(sugarField.getText());
            double fat = Double.parseDouble(fatsField.getText());

            c1.logFood(Model.getIdByUsername(username), foodName, Integer.parseInt(cals), protein, carbs, sugar, fat);
            frame.dispose();
            openWelcomePage(username);
        });


        viewAllButton.addActionListener(e -> {

            viewAllFoods(Model.getIdByUsername(username));
        });

        clearButton.addActionListener(e -> {

            int memberId = Model.getIdByUsername(username);
            c1.clearFoodInfoForMember(memberId);
            JOptionPane.showMessageDialog(frame, "Food information cleared successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        });


        frame.add(panel);


        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public void viewAllFoods(int memberId) {

        JFrame frame = new JFrame("All Consumed Foods and Total Macros");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel foodPanel = new JPanel();
        foodPanel.setLayout(new BoxLayout(foodPanel, BoxLayout.Y_AXIS));

        ArrayList<String> consumedFoods = c1.getFoodConsumed(memberId);


        for (String food : consumedFoods) {
            JLabel foodLabel = new JLabel(food);
            foodPanel.add(foodLabel);
        }

        double[] totalMacros = c1.getTotalMacros(memberId);
        double totalProtein = totalMacros[0];
        int totalCalories = (int) totalMacros[1];
        double totalCarbs = totalMacros[2];
        double totalSugar = totalMacros[3];
        double totalFats = totalMacros[4];

        JPanel macroPanel = new JPanel(new GridLayout(5, 2));
        macroPanel.add(new JLabel("Total Protein (g):"));
        macroPanel.add(new JLabel(String.valueOf(totalProtein)));
        macroPanel.add(new JLabel("Total Calories:"));
        macroPanel.add(new JLabel(String.valueOf(totalCalories)));
        macroPanel.add(new JLabel("Total Carbs (g):"));
        macroPanel.add(new JLabel(String.valueOf(totalCarbs)));
        macroPanel.add(new JLabel("Total Sugar (g):"));
        macroPanel.add(new JLabel(String.valueOf(totalSugar)));
        macroPanel.add(new JLabel("Total Fats (g):"));
        macroPanel.add(new JLabel(String.valueOf(totalFats)));


        JScrollPane foodScrollPane = new JScrollPane(foodPanel);
        frame.add(foodScrollPane, BorderLayout.CENTER);
        frame.add(macroPanel, BorderLayout.SOUTH);


        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void openExercisepage(String username) {

        JFrame exerciseFrame = new JFrame("Exercise Page");


        JLabel questionLabel = new JLabel("What did you do today?");
        questionLabel.setHorizontalAlignment(JLabel.CENTER);


        JButton weightLiftingButton = new JButton("Weight Lifting");
        JButton cardioButton = new JButton("Cardio");
        JButton backButton = new JButton("Back");


        exerciseFrame.setLayout(new BorderLayout());


        exerciseFrame.add(questionLabel, BorderLayout.NORTH);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(weightLiftingButton);
        buttonPanel.add(cardioButton);
        buttonPanel.add(backButton);
        exerciseFrame.add(buttonPanel, BorderLayout.CENTER);


        exerciseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exerciseFrame.setSize(400, 100);
        exerciseFrame.setResizable(false);
        exerciseFrame.setLocationRelativeTo(null);
        exerciseFrame.setVisible(true);


        weightLiftingButton.addActionListener(e -> {
            addStrengthTraining(username);
            exerciseFrame.dispose();
        });

        cardioButton.addActionListener(e -> {
            addCardioTraining(username);
            exerciseFrame.dispose();
        });

        backButton.addActionListener(e -> {
            openWelcomePage(username);
            exerciseFrame.dispose();
        });
    }

    private void addStrengthTraining(String username) {

        JFrame exerciseInputFrame = new JFrame("Strength Training Input");
        exerciseInputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exerciseInputFrame.setSize(400, 300);


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 3));

        JLabel nameLabel = new JLabel("Exercise Name:");
        JTextField nameField = new JTextField();
        panel.add(nameLabel);
        panel.add(nameField);

        JLabel setsLabel = new JLabel("Number of Sets:");
        JTextField setsField = new JTextField();
        panel.add(setsLabel);
        panel.add(setsField);

        JLabel repsLabel = new JLabel("Number of Reps:");
        JTextField repsField = new JTextField();
        panel.add(repsLabel);
        panel.add(repsField);

        JLabel caloriesLabel = new JLabel("Calories Burnt:");
        JTextField caloriesField = new JTextField();
        panel.add(caloriesLabel);
        panel.add(caloriesField);

        JButton clearButton = new JButton("Clear");
        JButton backButton = new JButton("Back");
        JButton showButton = new JButton("Show Exercises");
        JButton submitButton = new JButton("Submit");

        clearButton.addActionListener(clearEvent -> {
            c1.clearStrengthExcerises(Model.getIdByUsername(username));
        });

        backButton.addActionListener(clearEvent -> {
            exerciseInputFrame.dispose();
            openExercisepage(username);
        });

        panel.add(clearButton);
        panel.add(backButton);
        panel.add(showButton);
        panel.add(new JLabel());
        panel.add(submitButton);
        showButton.addActionListener(showEvent -> {

            displayWeightLiftingExercises(username);
        });
        submitButton.addActionListener(submitEvent -> {
            String exerciseName = nameField.getText();
            int numOfSets = Integer.parseInt(setsField.getText());
            int numOfReps = Integer.parseInt(repsField.getText());
            double caloriesBurnt = Double.parseDouble(caloriesField.getText());

            c1.addStrengthExercise(Model.getIdByUsername(username), exerciseName, numOfReps, numOfSets, caloriesBurnt);

            openWelcomePage(username);
            exerciseInputFrame.dispose();
        });

        exerciseInputFrame.add(panel);
        exerciseInputFrame.setVisible(true);
    }
    private void displayWeightLiftingExercises(String username) {

        ArrayList<String> weightLiftingExercises = c1.getAllWeightLiftingExercises(Model.getIdByUsername(username));

        JFrame exercisesFrame = new JFrame("Weight Lifting Exercises");
        exercisesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exercisesFrame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (String exercise : weightLiftingExercises) {
            JLabel exerciseLabel = new JLabel(exercise);
            panel.add(exerciseLabel);
        }
        exercisesFrame.add(panel);


        exercisesFrame.setVisible(true);
    }

    private void openSettingsPage() {

        this.dispose();


        JFrame settingsFrame = new JFrame("Settings");
        settingsFrame.setLayout(new BorderLayout());


        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);


        JPanel optionsPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton changePasswordButton = new JButton("Change Password");
        JButton changeUsernameButton = new JButton("Change Username");
        JButton deleteAccountButton = new JButton("Delete Account");
        JButton backButton = new JButton("Back");



        changePasswordButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null, "Do you want to proceed?", "Confirmation", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                changePassword();
            } else {
                System.out.println("User clicked NO.");

            }
            settingsFrame.dispose();
        });

        changeUsernameButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null, "Do you want to proceed?", "Confirmation", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                changeUsername();
            } else {
                System.out.println("User clicked NO.");

            }
            settingsFrame.dispose();
        });

        deleteAccountButton.addActionListener(e -> {
            deleteAccount();
            settingsFrame.dispose();
        });

        backButton.addActionListener(e->{
            settingsFrame.dispose();
            openWelcomePage(usernameField.getText());


        });


        optionsPanel.add(changePasswordButton);
        optionsPanel.add(changeUsernameButton);
        optionsPanel.add(deleteAccountButton);
        optionsPanel.add(backButton);


        settingsFrame.add(titlePanel, BorderLayout.NORTH);
        settingsFrame.add(optionsPanel, BorderLayout.CENTER);


        settingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        settingsFrame.pack();
        settingsFrame.setLocationRelativeTo(null);
        settingsFrame.setVisible(true);
    }

    public void displayUserInfo(double bodyWeight, double height, double goalWeight, int age, String gender) {

        JFrame userInfoFrame = new JFrame("User Information");
        userInfoFrame.setLayout(new BorderLayout());

        JLabel bodyWeightLabel = new JLabel("Body Weight: " + bodyWeight + " kg");
        JLabel heightLabel = new JLabel("Height: " + height + " cm");
        JLabel goalWeightLabel = new JLabel("Goal Weight: " + goalWeight + " kg");
        JLabel ageLabel = new JLabel("Age: " + age);
        JLabel genderLabel = new JLabel("Gender: " + gender);

        JPanel infoPanel = new JPanel(new GridLayout(5, 1));
        infoPanel.add(bodyWeightLabel);
        infoPanel.add(heightLabel);
        infoPanel.add(goalWeightLabel);
        infoPanel.add(ageLabel);
        infoPanel.add(genderLabel);
        JButton changeButton = new JButton("Change");
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Change button clicked!");
            }
        });


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(changeButton);


        userInfoFrame.add(infoPanel, BorderLayout.CENTER);
        userInfoFrame.add(buttonPanel, BorderLayout.SOUTH);


        userInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        userInfoFrame.pack();
        userInfoFrame.setLocationRelativeTo(null);
        userInfoFrame.setVisible(true);
    }
    private void addCardioTraining(String username) {

        JFrame exerciseInputFrame = new JFrame("Cardio Training Input");
        exerciseInputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exerciseInputFrame.setSize(400, 300);


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));

        JLabel nameLabel = new JLabel("Exercise Name:");
        JTextField nameField = new JTextField();
        panel.add(nameLabel);
        panel.add(nameField);

        JLabel durationLabel = new JLabel("Duration (minutes):");
        JTextField durationField = new JTextField();
        panel.add(durationLabel);
        panel.add(durationField);

        JLabel stepsLabel = new JLabel("Number of Steps:");
        JTextField stepsField = new JTextField();
        panel.add(stepsLabel);
        panel.add(stepsField);

        JLabel distanceLabel = new JLabel("Distance (KMs):");
        JTextField distanceField = new JTextField();
        panel.add(distanceLabel);
        panel.add(distanceField);

        JLabel caloriesLabel = new JLabel("Calories Burnt:");
        JTextField caloriesField = new JTextField();
        panel.add(caloriesLabel);
        panel.add(caloriesField);

        JButton backButton = new JButton("Back");
        JButton showButton = new JButton("Show Exercises");
        backButton.addActionListener(backEvent -> {
            exerciseInputFrame.dispose();
            openExercisepage(username);

        });

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(clearEvent -> {
            exerciseInputFrame.dispose();

        });
        showButton.addActionListener(showEvent -> {
            displayCardioExercises(username);
        });

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(submitEvent -> {
            c1.addCardoWorkout(Model.getIdByUsername(username),nameField.getText(),Integer.parseInt(durationField.getText()),Integer.parseInt(stepsField.getText()),Double.parseDouble(distanceField.getText()),Double.parseDouble(caloriesField.getText()));
            exerciseInputFrame.dispose();
            openWelcomePage(username);

        });
        panel.add(clearButton);
        panel.add(backButton);
        panel.add(showButton);
        panel.add(new JLabel());
        panel.add(submitButton);

        exerciseInputFrame.add(panel);
        exerciseInputFrame.setVisible(true);
    }
    private void displayCardioExercises(String username) {

        ArrayList<String> cardioExercises = c1.getAllCardioExercises(Model.getIdByUsername(username));
        JFrame exercisesFrame = new JFrame("Cardio Exercises");
        exercisesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exercisesFrame.setSize(400, 300);


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (String exercise : cardioExercises) {
            JLabel exerciseLabel = new JLabel(exercise);
            panel.add(exerciseLabel);
        }
        exercisesFrame.add(panel);
        exercisesFrame.setVisible(true);
    }
    private void changePassword() {

        JFrame changePasswordFrame = new JFrame("Password Change");
        changePasswordFrame.setLayout(new GridLayout(4, 2));

        JLabel oldPasswordLabel = new JLabel("Old Password:");
        JPasswordField oldPasswordField = new JPasswordField(20);
        changePasswordFrame.add(oldPasswordLabel);
        changePasswordFrame.add(oldPasswordField);

        JLabel newPasswordLabel = new JLabel("New Password:");
        JPasswordField newPasswordField = new JPasswordField(20);
        changePasswordFrame.add(newPasswordLabel);
        changePasswordFrame.add(newPasswordField);

        JLabel confirmNewLabel = new JLabel("Confirm New Password:");
        JPasswordField confirmNewField = new JPasswordField(20);
        changePasswordFrame.add(confirmNewLabel);
        changePasswordFrame.add(confirmNewField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                char[] oldPasswordChars = oldPasswordField.getPassword();
                String oldPassword = new String(oldPasswordChars);

                char[] newPasswordChars = newPasswordField.getPassword();
                String newPassword = new String(newPasswordChars);

                char[] confirmNewPasswordChars = confirmNewField.getPassword();
                String confirmNewPassword = new String(confirmNewPasswordChars);

                if(c1.changePassword(Model.getIdByUsername(usernameField.getText()),usernameField.getText() ,oldPassword, newPassword, confirmNewPassword)){
                    JOptionPane.showMessageDialog(null, "Password changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    changePasswordFrame.dispose();
                    openSignInScreen();
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong password or passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        changePasswordFrame.add(submitButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("");
            }
        });
        changePasswordFrame.add(backButton);

        changePasswordFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        changePasswordFrame.pack();
        changePasswordFrame.setVisible(true);
    }
    private void changeUsername() {

        JFrame changeUsernameFrame = new JFrame("Username Change");
        changeUsernameFrame.setLayout(new GridLayout(5, 2));

        JLabel usernameLabel = new JLabel("Current Username:");
        JTextField usernameField = new JTextField(20);
        changeUsernameFrame.add(usernameLabel);
        changeUsernameFrame.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        changeUsernameFrame.add(passwordLabel);
        changeUsernameFrame.add(passwordField);

        JLabel newUsernameLabel = new JLabel("New Username:");
        JTextField newUsernameField = new JTextField(20);
        changeUsernameFrame.add(newUsernameLabel);
        changeUsernameFrame.add(newUsernameField);

        JLabel confirmNewLabel = new JLabel("Confirm New Username:");
        JTextField confirmNewUsernameField = new JTextField(20);
        changeUsernameFrame.add(confirmNewLabel);
        changeUsernameFrame.add(confirmNewUsernameField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String newUsername = newUsernameField.getText();
                String confirmNewUsername = confirmNewUsernameField.getText();

                if (!newUsername.equals(confirmNewUsername)) {
                    JOptionPane.showMessageDialog(changeUsernameFrame, "New usernames do not match. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                    boolean success = c1.userNameChnage(Model.getIdByUsername(usernameField.getText()), newUsername, username, password);
                    if (success) {
                        JOptionPane.showMessageDialog(changeUsernameFrame, "Username successfully changed.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(changeUsernameFrame, "Failed to change username. Please check your username and password.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        changeUsernameFrame.add(submitButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeUsernameFrame.dispose();

                new View();
            }
        });
        changeUsernameFrame.add(backButton);


        changeUsernameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        changeUsernameFrame.pack();
        changeUsernameFrame.setLocationRelativeTo(null);
        changeUsernameFrame.setVisible(true);
    }


    private void deleteAccount() {

        JFrame deleteAccountFrame = new JFrame("Delete Account");
        deleteAccountFrame.setLayout(new GridLayout(3, 2));

        JLabel passwordLabel = new JLabel("Username:");
        JPasswordField confirmUsernameField = new JPasswordField(20);
        deleteAccountFrame.add(passwordLabel);
        deleteAccountFrame.add(confirmUsernameField);

        JLabel confirmLabel = new JLabel("Password:");
        JPasswordField confirmPasswordField = new JPasswordField(20);
        deleteAccountFrame.add(confirmLabel);
        deleteAccountFrame.add(confirmPasswordField);


        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!c1.deleteAccount(Model.getIdByUsername(usernameField.getText()), confirmUsernameField.getText(), confirmPasswordField.getText())) {
                    JOptionPane.showMessageDialog(null, "Wrong password or username. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    deleteAccountFrame.dispose();
                    new View();
                }

            }
        });
        deleteAccountFrame.add(confirmButton);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteAccountFrame.dispose();

                openSettingsPage();
            }
        });
        deleteAccountFrame.add(backButton);

        deleteAccountFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteAccountFrame.pack();
        deleteAccountFrame.setVisible(true);
    }
    protected void signInError(String error) {
        String errorMessage = "";
        if (error.equals("user")) {
            errorMessage = "Username does not exist. Please try again.";
        }
        if (error.equals("pass")) {
            errorMessage = "Incorrect password. Please try again.";
        }

        Object[] options = {"OK"};

        int choice = JOptionPane.showOptionDialog(null, errorMessage, "Sign In Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == JOptionPane.OK_OPTION) {
            signInFrame.dispose();
            new View();
        }
    }

    public void openMembersList(String username) {
        dispose();
        String trainer_name = username;
        members = c1.showMembers(Model.getIdByUsername(username));
        JList<String> membersList = new JList<>(members.toArray(new String[0]));
        JScrollPane scrollPane = new JScrollPane(membersList);
        JButton backButton = new JButton("Back");

        getContentPane().removeAll();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(backButton, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            openTrainerWelcomePage(trainer_name);

            dispose();
        });
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void openTrainersList(String username) {
        members = c1.showTrainer();
        JList<String> trainersList = new JList<>(members.toArray(new String[0]));
        JScrollPane scrollPane = new JScrollPane(trainersList);
        JButton backButton = new JButton("Back");
        JButton selectButton = new JButton("Select");
        JButton cancelButton = new JButton("Cancel All");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(selectButton);
        buttonPanel.add(backButton);
        buttonPanel.add(cancelButton);
        getContentPane().removeAll();

        getContentPane().setLayout(new BorderLayout());

        getContentPane().add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            openWelcomePage(username);
            dispose();
        });

        selectButton.addActionListener(e -> {
            String selectedTrainer = trainersList.getSelectedValue();
            if (selectedTrainer != null) {
                c1.addTrainer(username, selectedTrainer);
                System.out.println("Selected trainer: " + selectedTrainer);
                openTrainerAvailabilityPage(selectedTrainer);
            } else {
                System.out.println("No trainer selected.");
            }
        });

        cancelButton.addActionListener(e -> {
            c1.unbookTrainer(Model.getIdByUsername(username));
            JOptionPane.showMessageDialog(scrollPane, "Cancelled Trainers");
            dispose();
            openTrainersList(username);


        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openAvailabilityPage(String username) {
        String trainerName = usernameField.getText();
        JFrame availabilityFrame = new JFrame("Set Availability - " + username);
        availabilityFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        availabilityFrame.setSize(400, 300);
        availabilityFrame.setLayout(new BorderLayout());

        JPanel slotsPanel = new JPanel(new GridLayout(11, 1));

        for (int i = 0; i < 10; i++) {
            int hour = i + 8;
            String label = hour + ":00-" + (hour + 1) + ":00";
            JCheckBox checkbox = new JCheckBox(label);
            slotsPanel.add(checkbox);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton submitButton = new JButton("Submit");
        JButton backButton = new JButton("Back");

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> selectedTimes = new ArrayList<>();
                Component[] components = slotsPanel.getComponents();
                for (Component component : components) {
                    if (component instanceof JCheckBox) {
                        JCheckBox checkbox = (JCheckBox) component;
                        if (checkbox.isSelected()) {
                            selectedTimes.add(checkbox.getText());
                            checkbox.setEnabled(false);
                        }
                    }
                }
                String[] classNames = {"Yoga", "Calisthenics", "Weight Lifting", "Cardio"};
                String selectedClass = (String) JOptionPane.showInputDialog(availabilityFrame, "Select a class:",
                        "Class Selection", JOptionPane.QUESTION_MESSAGE, null, classNames, classNames[0]);
                if (selectedClass != null) {
                    c1.setSchedule(Model.getIdByUsername(username), selectedTimes);
                    c1.setClassSchedule(selectedClass,selectedTimes);
                    JOptionPane.showMessageDialog(availabilityFrame, "Availability submitted successfully!");
                } else {
                    JOptionPane.showMessageDialog(availabilityFrame, "Class selection canceled.");
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                availabilityFrame.dispose();
                openTrainerWelcomePage(trainerName);

            }
        });

        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);

        availabilityFrame.add(slotsPanel, BorderLayout.CENTER);
        availabilityFrame.add(buttonPanel, BorderLayout.SOUTH);

        availabilityFrame.setVisible(true);
    }

    public void openTrainerAvailabilityPage(String trainerUsername) {
        ArrayList<String> availableTimes = c1.getAvailableTimeSlotsForTrainer(Model.getIdByUsername(trainerUsername));
        JFrame availabilityFrame = new JFrame("Trainer Availability");
        JPanel availabilityPanel = new JPanel(new GridLayout(0, 2));

        for (String time : availableTimes) {
            JButton bookButton = new JButton("Book");
            JLabel timeLabel = new JLabel(time);


            bookButton.addActionListener(e -> {
                c1.bookTrainerTime(Model.getIdByUsername(trainerUsername),time);
                bookButton.setEnabled(false);
                JOptionPane.showMessageDialog(availabilityFrame,"Class booked successfully.");

            });

            availabilityPanel.add(timeLabel);
            availabilityPanel.add(bookButton);
        }

        availabilityFrame.getContentPane().add(availabilityPanel);

        availabilityFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        availabilityFrame.pack();
        availabilityFrame.setLocationRelativeTo(null);
        availabilityFrame.setVisible(true);
    }
    public void openBookRoomPage(String username) {
        ArrayList<Integer> roomsWithTrueValue = c1.getRoomV2();
        Collections.sort(roomsWithTrueValue);

        JFrame frame = new JFrame("Room Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());


        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Integer roomNumber : roomsWithTrueValue) {
            listModel.addElement("Room " + roomNumber);
        }
        JList<String> roomList = new JList<>(listModel);
        roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(roomList);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");
        JButton backButton = new JButton("Back");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!roomList.isSelectionEmpty()) {
                    String selectedRoom = roomList.getSelectedValue();
                    int roomNumber = Integer.parseInt(selectedRoom.substring(selectedRoom.indexOf(" ") + 1));
                    displayListOfRooms(roomNumber, username);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a room.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                openStaffWelcomePage(username);
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);

        frame.add(listPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void openUnbookRoomPage(String username) {

        ArrayList<Integer> roomsWithTrueValue = c1.getRooms();
        Collections.sort(roomsWithTrueValue);

        JFrame frame = new JFrame("Room Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());


        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Integer roomNumber : roomsWithTrueValue) {
            listModel.addElement("Room " + roomNumber);
        }
        JList<String> roomList = new JList<>(listModel);
        roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(roomList);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");
        JButton backButton = new JButton("Back");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!roomList.isSelectionEmpty()) {
                    String selectedRoom = roomList.getSelectedValue();
                    int roomNumber = Integer.parseInt(selectedRoom.substring(selectedRoom.indexOf(" ") + 1));
                    displayListOfRoomTimesUnbook(roomNumber,username);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a room.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                openStaffWelcomePage(username);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);
        frame.add(listPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public void displayListOfRoomTimesUnbook(int roomNum,String username) {
        ArrayList<String> timesWithTrueValue = c1.getTimeRoomNum(roomNum);

        JFrame frame = new JFrame("Room " + roomNum + " Times Selection");
        frame.setPreferredSize(new Dimension(200, 200));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel checkboxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        checkboxPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ArrayList<JCheckBox> checkboxes = new ArrayList<>();
        for (String time : timesWithTrueValue) {
            JCheckBox checkBox = new JCheckBox(time);
            checkboxPanel.add(checkBox);
            checkboxes.add(checkBox);
        }

        JButton submitButton = new JButton("Submit");
        JButton backButton = new JButton("Back");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> selectedTimes = new ArrayList<>();
                for (JCheckBox checkBox : checkboxes) {
                    if (checkBox.isSelected()) {
                        selectedTimes.add(checkBox.getText());
                    }
                }

                c1.unbookRoom(roomNum, selectedTimes);
                frame.dispose();
                openUnbookRoomPage(username);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                openUnbookRoomPage(username);
            }
        });


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);


        frame.add(checkboxPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);


        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void displayListOfRooms(int roomNum, String username) {
        ArrayList<String> timesWithTrueValue = c1.getTimeRoomNumV2(roomNum);

        JFrame frame = new JFrame("Room " + roomNum + " Times Selection");
        frame.setPreferredSize(new Dimension(200, 200));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel checkboxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        checkboxPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        ArrayList<JCheckBox> checkboxes = new ArrayList<>();
        for (String time : timesWithTrueValue) {
            JCheckBox checkBox = new JCheckBox(time);
            checkboxPanel.add(checkBox);
            checkboxes.add(checkBox);
        }


        JButton submitButton = new JButton("Submit");
        JButton backButton = new JButton("Back");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String className = JOptionPane.showInputDialog(frame, "Enter the type of class:", "Class Type", JOptionPane.PLAIN_MESSAGE);
                if (className != null && !className.isEmpty()) {
                    ArrayList<String> selectedTimes = new ArrayList<>();

                    for (JCheckBox checkBox : checkboxes) {
                        if (checkBox.isSelected()) {
                            selectedTimes.add(checkBox.getText());
                        }
                    }

                    c1.bookRoom(roomNum, selectedTimes);
                    c1.scheduleWorkoutClass(className,roomNum,selectedTimes);
                    frame.dispose();
                    openStaffWelcomePage(username);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid class type.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                openStaffWelcomePage(usernameField.getText());
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);


        frame.add(checkboxPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);


        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    protected void openFitnessAchievementPage(HashMap<String, String> exerciseRecords) {
        JFrame achievementFrame = new JFrame("Fitness Achievement");
        achievementFrame.setLayout(new BorderLayout());


        JPanel recordPanel = new JPanel(new GridLayout(exerciseRecords.size(), 1));
        for (Map.Entry<String, String> entry : exerciseRecords.entrySet()) {
            JLabel recordLabel = new JLabel(entry.getKey() + ": " + entry.getValue());
            recordPanel.add(recordLabel);
        }


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addUpdateButton = new JButton("Add/Update Record");
        JButton backButton = new JButton("Back");


        addUpdateButton.addActionListener(e -> {
            openAddUpdateRecordDialog();
            dispose();
        });

        backButton.addActionListener(e -> {
            achievementFrame.dispose();
        });

        buttonPanel.add(addUpdateButton);
        buttonPanel.add(backButton);
        achievementFrame.add(recordPanel, BorderLayout.CENTER);
        achievementFrame.add(buttonPanel, BorderLayout.SOUTH);

        achievementFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        achievementFrame.pack();
        achievementFrame.setLocationRelativeTo(null);
        achievementFrame.setVisible(true);
    }
    private void openAddUpdateRecordDialog() {

        JDialog dialog = new JDialog();
        dialog.setTitle("Add/Update Record");
        JPanel dialogPanel = new JPanel(new GridLayout(3, 2));

        JTextField workoutNameField = new JTextField();
        JTextField recordField = new JTextField();

        dialogPanel.add(new JLabel("Workout Name:"));
        dialogPanel.add(workoutNameField);
        dialogPanel.add(new JLabel("Current Record:"));
        dialogPanel.add(recordField);

        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        submitButton.addActionListener(submitEvent -> {
            String workoutName = workoutNameField.getText();
            String record = recordField.getText();
            try {
                c1.addOrUpdateMemberRecord(Model.getIdByUsername(usernameField.getText()), workoutName, record);
                dialog.dispose();
                openFitnessAchievementPage(c1.extractAchievements(Model.getIdByUsername(usernameField.getText())));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        cancelButton.addActionListener(cancelEvent -> {
            dialog.dispose();
        });

        dialogPanel.add(submitButton);
        dialogPanel.add(cancelButton);
        dialog.add(dialogPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    public void openStatsPage(ArrayList<String> stats) {
        JFrame statsFrame = new JFrame("Member Stats");
        statsFrame.setLayout(new GridLayout(7, 2));


        JLabel bodyWeightLabel = new JLabel("Body Weight: " + stats.get(0));
        JLabel heightLabel = new JLabel("Height: " + stats.get(1));
        JLabel genderLabel = new JLabel("Gender: " + stats.get(2));
        JLabel ageLabel = new JLabel("Age: " + stats.get(3));
        JLabel goalWeightLabel = new JLabel("Goal Weight: " + stats.get(4));


        statsFrame.add(bodyWeightLabel);
        statsFrame.add(heightLabel);
        statsFrame.add(genderLabel);
        statsFrame.add(ageLabel);
        statsFrame.add(goalWeightLabel);


        JButton backButton = new JButton("Back");
        JButton editStatsButton = new JButton("Edit Stats");


        backButton.addActionListener(e -> {
            statsFrame.dispose();
        });

        editStatsButton.addActionListener(e -> {
            statsFrame.dispose();
            openEditStatsPage();
        });


        statsFrame.add(backButton);
        statsFrame.add(editStatsButton);


        statsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        statsFrame.pack();
        statsFrame.setLocationRelativeTo(null);
        statsFrame.setVisible(true);
    }
    public void openEditStatsPage() {
        JFrame editStatsFrame = new JFrame("Edit Member Stats");
        editStatsFrame.setLayout(new GridLayout(7, 2));


        JLabel bodyWeightLabel = new JLabel("Body Weight:");
        JTextField bodyWeightField = new JTextField();

        JLabel heightLabel = new JLabel("Height:");
        JTextField heightField = new JTextField();

        JLabel genderLabel = new JLabel("Gender:");
        JTextField genderField = new JTextField();

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();

        JLabel goalWeightLabel = new JLabel("Goal Weight:");
        JTextField goalWeightField = new JTextField();


        editStatsFrame.add(bodyWeightLabel);
        editStatsFrame.add(bodyWeightField);
        editStatsFrame.add(heightLabel);
        editStatsFrame.add(heightField);
        editStatsFrame.add(genderLabel);
        editStatsFrame.add(genderField);
        editStatsFrame.add(ageLabel);
        editStatsFrame.add(ageField);
        editStatsFrame.add(goalWeightLabel);
        editStatsFrame.add(goalWeightField);


        JButton submitButton = new JButton("Submit");
        JButton backButton = new JButton("Back");


        submitButton.addActionListener(e -> {

            double newBodyWeight = Double.parseDouble(bodyWeightField.getText());
            double newHeight = Double.parseDouble(heightField.getText());
            String newGender = genderField.getText();
            int newAge = Integer.parseInt(ageField.getText());
            double newGoalWeight = Double.parseDouble(goalWeightField.getText());


            c1.editMemberDetails(Model.getIdByUsername(usernameField.getText()), newBodyWeight, newHeight, newGender, newAge, newGoalWeight);


            editStatsFrame.dispose();


            openStatsPage(c1.memberDetails(Model.getIdByUsername(usernameField.getText())));
        });

        backButton.addActionListener(e -> {
            editStatsFrame.dispose();
        });


        editStatsFrame.add(submitButton);
        editStatsFrame.add(backButton);


        editStatsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editStatsFrame.pack();
        editStatsFrame.setLocationRelativeTo(null);
        editStatsFrame.setVisible(true);
    }
    public void openBookClassGUI(String username) {
        JFrame frame = new JFrame("Class Booking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        ArrayList<String> availableClasses = c1.getAllAvailableClasses();


        DefaultListModel<String> classListModel = new DefaultListModel<>();
        for (String className : availableClasses) {
            classListModel.addElement(className);
        }


        JList<String> classList = new JList<>(classListModel);
        JScrollPane scrollPane = new JScrollPane(classList);


        JButton bookButton = new JButton("Book Class");

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedClass = classList.getSelectedValue();
                if (selectedClass != null) {
                    ArrayList<String> availableTimes = c1.getAvailableTimesForClass(selectedClass);
                    if (!availableTimes.isEmpty()) {
                        String[] availableTimesArray = availableTimes.toArray(new String[0]);
                        String selectedTime = (String) JOptionPane.showInputDialog(frame,
                                "Select class time:", "Class Time", JOptionPane.PLAIN_MESSAGE, null,
                                availableTimesArray, availableTimesArray[0]);
                        if (selectedTime != null && !selectedTime.isEmpty()) {
                            int memberId = Model.getIdByUsername(username);
                            if (memberId != -1) {
                                c1.addBookedClass(memberId, selectedClass, selectedTime);
                                JOptionPane.showMessageDialog(frame, "Class booked successfully.");
                            } else {
                                JOptionPane.showMessageDialog(frame, "Error: Member ID not found.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "Please select a class time.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "No available times for this class.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a class to book.");
                }
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(bookButton);
        buttonPanel.add(backButton);


        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);


        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}



