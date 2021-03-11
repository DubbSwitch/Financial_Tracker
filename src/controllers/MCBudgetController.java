package controllers;

import lib.ConsoleIO;
import models.Budget;
import models.FileConfigurations;
import models.IODataModel;
import models.User;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MCBudgetController {
    private static final FileConfigurations fileConfigurations = new FileConfigurations();
    static User user; // This variable is being used to test methods while data persistence is still being setup
    static IODataModel iodataModel = new IODataModel(); //Use for saving data, and managing users.
    static UserContextController UserContextController = new UserContextController();
    static User contextUser; //current user
    private final ArrayList<Budget> budgetList = new ArrayList<>();

    public static void run() {
        int choice = homeMenu();
        homeSwitch(choice);
    }
    //finished
    private static int homeMenu() {
        String[] options = {"Login", "Create New User", "Reset Password", "Load", "Save"};
        return ConsoleIO.promptForMenuSelection(" ", options, true);
    }
    private static void homeSwitch(int choice) {
        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                createUser();
                break;
            case 3:
                resetPassword();
                break;
            case 4:
                load();
                break;
            case 5:
                save();
                break;
            case 0:
                System.out.println("Goodbye");
                break;
            default:
                System.out.println("I don't know how you did that. Your input =" + choice);
        }
    }

    //Finished
    private static void login() {
        String username = ConsoleIO.promptForString("Enter your Username: ", false);
        // added a try catch because if their was no users created it would cause a NulLPointerError
        String password = ConsoleIO.promptForString("Enter your password: ", false);
        boolean success = false;
        try {
            contextUser = UserContextController.findUser(iodataModel, username);
            if (contextUser.getUserName().equals(username) && contextUser.getPassword().equals(password)) {
                success = true;
            } else {
                System.out.println("Password was incorrect: ");

            }
        } catch (NullPointerException nfe) {
            System.out.println("User: '" + username + "', does not exist.");
        }
        if(success){
            int choice = userMenu();
            userSwitch(choice);
        }
        else{run();}
    }
    //Finished
    private static void createUser() {
        String pED = "Please enter desired";
        String userName = ConsoleIO.promptForString(pED + " username: ", false);
        String password = ConsoleIO.promptForString(pED + " password: ", true);
        String question = ConsoleIO.promptForString("Security Question for password reset\n" + pED + " Question: ", false);
        String answer = ConsoleIO.promptForString(pED + " answer: ", false);
        String displayName = ConsoleIO.promptForString(pED + " display name: ", false);
        // user is a place holder strictly for testing
        // the end of this method should create a new file with the user's information.
        //TODO validate save
        contextUser = new User(userName, displayName, password, question, answer);
        iodataModel.addUser(contextUser); //Make sure the user is saved
        run();
    }

    //Finished
    private static void resetPassword() {
        String username = ConsoleIO.promptForString("Enter your username: ", false);
        try {
            contextUser = UserContextController.findUser(iodataModel, username);
            if (contextUser.getUserName().equals(username)) {
                String answer = ConsoleIO.promptForString(contextUser.getSecQuestion() + ": ", false);
                if (answer.equals(contextUser.getSecAnswer())) {
                    String password = ConsoleIO.promptForString("Enter your new password: ", false);
                    contextUser.setPassword(password);
                } else
                    System.out.println("The answer to your security question was incorrect.");
            } else
                System.out.println("User '" + username + "' not found.");
        } catch (NullPointerException nfe) {
            System.out.println("User '" + username + "' does not exist. *** TESTING = NFE has occurred.");
            //run();
        }
        run();
        //checks if that user exists
        //IF user exists
        // Prompt their security question
        // IF user input is true
        // Prompt user for new password and set it as the users new password
        //else go back to homeMenu
    }

    //Finished
    private static int userMenu() {
        String[] menu = {"Budgeting", "Account Settings", "Logout"};
        return ConsoleIO.promptForMenuSelection("Welcome "  + contextUser.getDisplayName() + ",\n",menu, false);
    }

    //      //
    // USER //
    //      //

    //Finished
    private static void userSwitch(int choice) {
        int input;
        switch (choice) {
            case 1:
                input = budgetingMenu();
                budgetingSwitch(input);
                break;
            case 2:
                input = accountSettingsMenu();
                accountSettingsSwitch(input);
                break;
            case 3:
                logout();
        }
    }

    //Finished
    private static void logout() {
        contextUser = null;
        run();
    }

    //Finished
    private static int budgetingMenu() {
        String[] menu = {"Select Budget", "Create Budget", "Savings"};
        return ConsoleIO.promptForMenuSelection(" ", menu, true);
    }

    //           //
    // BUDGETING //
    //           //

    //Finished
    private static void budgetingSwitch(int choice) {
        int input;
        switch (choice) {
            case 1:
                chooseBudget();
                break;
            case 2:
                String name = ConsoleIO.promptForString("Enter name of budget: ", false);
                int funds = ConsoleIO.promptForInt("Amount of money usable: ", 1, 200000000);
                createBudget(funds, name);
                int path = budgetingMenu();
                budgetingSwitch(path);
                break;
            case 3:
                savingsMenu();
                break;
            case 0:
                input = userMenu();
                userSwitch(input);
        }
    }
    // Untested, TODO finish createBudget method
    private static void chooseBudget() {
        //Creates an array containing the names of all the user's budgets, for later menu display
        try {
            String[] menu = new String[contextUser.getBudgetList().size()];
            for (int i = 0; i < menu.length; i++) {
                menu[i] = contextUser.getBudgetList().get(i).getName();
            }

            int input = ConsoleIO.promptForMenuSelection("Please select the budget you'd like to view:", menu, false);
            budgetOptionsSwitch(budgetOptionsMenu(), contextUser.getBudgetList().get(input - 1));
        }catch (NullPointerException nfe){
            System.out.println("There are no budgets associated with your account");
            int path = budgetingMenu();
            budgetingSwitch(path);
        }
    }

    //TODO write method
    private static void createBudget(double maxAmount, String name) {
        contextUser.addNewBudget(new Budget(maxAmount,name));
    }

    //TODO write method
    private static void savingsMenu() {

    }

    //         //
    // SAVINGS //
    //         //

    //TODO write method
    private static void modifySavingsAmount(double amount, int choice) {

    }

    //       //
    // MENUS //
    //       //

    //Finished
    private static int budgetOptionsMenu() {
        String[] menu = {"Modify Budget","View History","Rename Budget","Delete Budget"};
        return ConsoleIO.promptForMenuSelection("", menu,true);
    }

    //Finished
    private static void budgetOptionsSwitch(int choice, Budget budget) {
        switch (choice) {
            case 1:
                modifyBudgetMenu();
                break;
            case 2:
                viewTransactionHistory(budget);
                break;
            case 3:
                renameBudget(budget, ConsoleIO.promptForString("Please enter a new name for the budget:",false));
                break;
            case 4:
                String response = ConsoleIO.promptForString("Warning: Deleting a budget and its full transaction history is permanent and cannot be undone.\nTo confirm deletion, please enter your password.", true);
                if (response.equals(contextUser.getPassword())) {
                    deleteBudget(budget);
                } else {
                    System.out.println("Incorrect password. Your budget has not been deleted.");
                    budgetOptionsSwitch(budgetOptionsMenu(), budget);
                }
                break;
        }
    }

    //TODO write method
    private static void modifyBudgetMenu() {

    }

    //TODO write method
    private static void viewTransactionHistory(Budget budget) {

    }

    //TODO write method
    private static void deleteBudget(Budget budget) {

    }

    //TODO write method
    private static void renameBudget(Budget budget, String newName) {

    }

    //Finished
    private static int accountSettingsMenu() {
        String[] menu = {"Change Display Name","Change Password","Change Change Security Question and Answer"};
        return ConsoleIO.promptForMenuSelection("Welcome to your account settings " + contextUser.getDisplayName()+ ",\n",menu,true);
    }

    //         //
    // ACCOUNT //
    //         //

    //Finished
    private static void accountSettingsSwitch(int choice) {
        switch (choice){
            case 1:
                String displayName = ConsoleIO.promptForString("Please enter new name: ",false);
                changeDisplayName(displayName);
                break;
            case 2:
                String password = ConsoleIO.promptForString("Please enter new password: ",false);
                changePassword(password);
                break;
            case 3:
                String question = ConsoleIO.promptForString("Enter your new security question: ",false);
                String answer = ConsoleIO.promptForString("Enter the to your new security answer: ",false);
                changeSecQnA(question,answer);
                break;
            default:
                // brings the user back to the previous menu
                int input = userMenu();
                userSwitch(input);
                break;
        }
    }

    //Finished
    private static void changeDisplayName(String newName) { // TESTED: works as intended
        contextUser.setDisplayName(newName);
        userSwitch(2); // This automatically takes the user back to the account settings menu
    }

    //Finished
    private static void changePassword(String newPassword) { //TESTED: works as intended
        contextUser.setPassword(newPassword);
        userSwitch(2);
    }

    //Finished
    private static void changeSecQnA(String question, String answer) {
        contextUser.setSecQuestion(question);
        contextUser.setSecAnswer(answer);
        userSwitch(2);
    }

    private static void load() {
        try {
            iodataModel = new FileController().readDirectory(fileConfigurations);
            run();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //                  //
    // Data Persistence //
    //                  //

    private static void save() {
        int swc = 2;
        if (fileConfigurations.getPath() != null) {
            System.out.println("Use saved path: '" + fileConfigurations.getPath() + "'?");
            ConsoleIO.promptForMenuSelection(" ", new String[]{"Yes", "No"}, false);
        }
        if (swc == 2)
            fileConfigurations.setPath(Paths.get(ConsoleIO.promptForString("Path to save data to: ", false)));
        try {
            new FileController().writeToFile(iodataModel, fileConfigurations);
            System.out.println("Saved!");
            run();
        } catch (IOException e) {
            System.out.println("Failed to save data.");
            //e.printStackTrace();
        }
    }
}
