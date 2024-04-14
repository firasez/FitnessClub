import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {
    private View view;
    private Model model1;

    public Controller() {

        model1 = new Model();
        view = new View();
        view.setController(this);
    }


    protected void signUp(String name, String lastname, String userename, char[] password,String accountType) {

        String pass = new String(password);
        model1.addAccount(name,lastname,userename,pass,accountType);

    }
    protected void submitInfo(String username, int id, double weight, double height, double goalWeight, int age, String gender){
        model1.addInfo(id, weight, height, goalWeight, age, gender);
        view.openWelcomePage(username);
    }
    protected void signIn(String username, char[] password) {
        String pass = new String(password);

        if(model1.verifyUsername(username) &&  model1.verifyPassword(username,pass)){
            if (model1.verifyAccountType(username, "Member")) {
                System.out.println("Logged in");
                view.openWelcomePage(username);
            } else if (model1.verifyAccountType(username, "Staff")) {
                System.out.println("Logged in");
                view.openStaffWelcomePage(username);
            } else if (model1.verifyAccountType(username, "Trainer")) {
                System.out.println("Logged in");
                view.openTrainerWelcomePage(username);
            }
        } else if (!model1.verifyUsername(username)) {
            view.signInError("user");
        } else if (!model1.verifyPassword(username,pass)) {
            view.signInError("pass");
        }
    }


    protected void logFood(int id, String foodName, int cals, double p, double c, double s, double f){
        model1.addFood(id, foodName, cals, p, c, s, f);
    }

    protected void addStrengthExercise(int accountId, String workoutName, int reps, int sets, double caloriesBurnt){
        model1.addWeightLiftingInfo(accountId,workoutName,reps,sets,caloriesBurnt);
    }

    protected void clearStrengthExcerises(int accountId){
        model1.clearExercisesForPerson(accountId);
    }

    protected void addCardoWorkout(int accountId, String exerciseName, int durationMinutes, int steps, double distance, double caloriesBurnt){
        model1.addCardioExercise(accountId,exerciseName,durationMinutes,steps,distance,caloriesBurnt);
    }

    protected boolean userNameChnage(int memberId, String newUsername, String currentUsername, String password){
        return model1.changeUsername(memberId,newUsername,currentUsername,password);
    }
    public static void main(String[] args) {
        Controller controller = new Controller();
    }
    protected ArrayList<String> showMembers(int id){
        return model1.getTrainersById(id);
    }
    protected ArrayList<String> showTrainer(){
        return model1.getTrainer();
    }

    protected boolean deleteAccount(int memberId, String username, String password){
        return model1.deleteAccount(memberId,username,password);

    }

    protected void addTrainer(String memberUsername, String trainerUsername) {
        int memberId = model1.getIdByUsername(memberUsername);
        model1.addTrainerById(memberId, trainerUsername);
    }

    public boolean verifyUsername(String username) {
        return model1.verifyUsername(username);
    }

    public boolean verifyPassword(String username, String password) {
        return model1.verifyPassword(username, password);
    }

    public void setSchedule(int trainerId, ArrayList<String> selectedSlots){
        model1.setTrainerAvailability(trainerId,selectedSlots);
    }

    public void createTrainer(int trainerId){
        model1.addTrainerToAvailabilityTable( trainerId);
    }

    public ArrayList getAvailableTimeSlotsForTrainer(int trainerId){
        return model1.getAvailableTimeSlotsForTrainer(trainerId);
    }

    public ArrayList getAvailableClasses(){
        return model1.getAvailableTimeSlotsForClass();
    }

    public boolean bookTrainerTime(int trainerId, String timeSlot){
        return model1.bookTrainerTime(trainerId,timeSlot);
    }
    public boolean bookClass(String classtime, String timeSlot){
        return model1.bookClassTime(classtime,timeSlot);
    }

    public void setClassSchedule(String c, ArrayList<String> time){
        model1.updateClassSchedule(c,time);

    }

    public void setEquipmentStatus(String name, String status) {
        model1.setEquipmentStatus(name, status);
    }
    public String getEquipmentStatus(String equipment){
        return model1.getStatus(equipment);
    }

    public ArrayList<Integer> findRoom(ArrayList<String> room, int id){
        return model1.addRooms(room, id);
    }

    public void bookRoom(int roomNum, ArrayList<String> times){
        model1.bookRooms(roomNum,times);
    }

    public ArrayList<String> getTimeRoomNumV2(int roomNum) {
        return model1.getAvailableTimeSlots(roomNum);
    }

    public ArrayList<String> getMembers(){
        return model1.getMembers();
    }

    public void payBill(int id) {
        model1.payBill(id);
    }

    public boolean getBill(int id) {
        return model1.getBill(id);
    }

    public void createBill(int id) {
        model1.createBill(id);
    }

    public ArrayList<String> getStaffRoomTimes(int id) {
        return model1.getStaffRoomTimes(id);
    }

    public HashMap<String, String> extractAchievements(int memberId) throws SQLException {
        return model1.extractMemberRecords(memberId);

    }

    public void addOrUpdateMemberRecord(int memberId, String workoutName, String record) throws SQLException {
        model1.addOrUpdateMemberRecord(memberId,workoutName,record);

    }

    public void unbookRoom(int roomNum, ArrayList<String> times) {
        model1.unbookRoom(roomNum, times);
    }

    public ArrayList<String> getTimesWithRooms() {
        return model1.getTimeSlotsWithTrueValue();
    }

    public ArrayList<String> memberDetails(int memberId) {
        return model1.getMemberDetails(memberId);
    }

    public ArrayList<Integer> getRooms() {
        return model1.getRooms();
    }
    public void editMemberDetails(int memberId, double bodyWeight, double height, String gender, int age, double goalWeight) {
        model1.editMemberDetails(memberId,bodyWeight,height,gender,age,goalWeight);
    }
    public double calculateOverallCalories(int memberId) {
        return model1.calculateOverallCalories(memberId);
    }

    public ArrayList<String> getTimeRoomNum(int roomNum) {
        return model1.getTimeRoomNum(roomNum);
    }
    public int calculateOverallSteps(int memberId) {
        return model1.calculateOverallSteps(memberId);
    }
    public boolean changePassword(int memberId,String username, String oldPassword, String newPassword, String confirmNewPassword) {
        return model1.changePassword(memberId,username,oldPassword,newPassword,confirmNewPassword);
    }

    public ArrayList<Integer> getRoomV2() {
        return model1.getRoomV2();
    }
    public double[] getTotalMacros(int memberId) {
        return model1.getTotalMacros(memberId);
    }
    public ArrayList<String> getFoodConsumed( int memberId) {
        return model1.getFoodConsumed(memberId);
    }
    public ArrayList<String> getAllWeightLiftingExercises(int memberId) {
        return model1.getAllWeightLiftingExercises(memberId);
    }
    public ArrayList<String> getAllCardioExercises(int memberId) {
        return model1.getAllCardioExercises(memberId);
    }
    public void clearFoodInfoForMember(int memberId) {
        model1.clearFoodInfoForMember(memberId);
    }
    public ArrayList<String> getAllAvailableClasses() {
        return model1.getAllAvailableWorkoutClasses();
    }
    public void addBookedClass(int memberId, String className, String classTime) {
        model1.addBookedWorkoutClass(memberId,className,classTime);
    }
    public void scheduleWorkoutClass(String className, int roomNumber, ArrayList<String> times) {
        model1.scheduleWorkoutClass(className,roomNumber,times);
    }
    public ArrayList<String> getAvailableTimesForClass(String classname) {
        return model1.getTrueTimes(classname);
    }
    public boolean unbookTrainer(int username){
        return model1.unbookTrainer(username);
    }
}
