package controllers;

import lib.ConsoleIO;
import models.Budget;
import models.FileConfigurations;
import models.IODataModel;
import models.User;

import java.io.IOException;
import java.nio.file.Paths;

public class MCBudgetController {
    private static final FileConfigurations fileConfigurations = new FileConfigurations();
    static User user; // This variable is being used to test methods while data persistence is still being setup
    static IODataModel iodataModel = new IODataModel(); //Use for saving data, and managing users.
    static UserContextController UserContextController = new UserContextController();
    static User contextUser; //current user

    public static void run() {
        int choice = homeMenu();
        homeSwitch(choice);
    }

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

    //TODO write method
    private static void login() {
        String username = ConsoleIO.promptForString("Enter your Username: ", false);
        // added a try catch because if their was no users created it would cause a NulLPointerError
        String password = ConsoleIO.promptForString("Enter your password: ", false);
        try {
            contextUser = UserContextController.findUser(iodataModel, username);
            if (contextUser.getUserName().equals(username) && contextUser.getPassword().equals(password)) {
                int choice = userMenu();
                userSwitch(choice);
            } else {
                System.out.println("Password was incorrect: ");
                run();
            }
        } catch (NullPointerException nfe) {
            System.out.println("User: '" + username + "', does not exist.");
            run();
        }
    }

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

    //TODO write method
    private static void resetPassword() {
        // prompts the user for their username
        String username = ConsoleIO.promptForString("Enter your username: ", false);
        contextUser = new UserContextController().findUser(iodataModel, username);
        try {
            if (username.equals(contextUser.getUserName())) {
                String answer = ConsoleIO.promptForString(user.getSecQuestion() + " : ", false);
                if (answer.equals(contextUser.getSecAnswer())) {
                    String password = ConsoleIO.promptForString("Enter your new password: ", false);
                    contextUser.setPassword(password);
                } else
                    System.out.println("The answer to your security question was incorrect.");
            } else
                System.out.println("User '" + username + "' not found.");
        } catch (NullPointerException nfe) {
            System.out.println("User '" + username + "' does not exist.");
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

    //TODO write method
    private static int userMenu() {
        String[] menu = {"Budgeting", "Account Settings", "Logout"};
        return ConsoleIO.promptForMenuSelection(" ",menu, false);
    }

    //      //
    // USER //
    //      //

    //TODO write method
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

    //TODO write method
    private static void logout() {
        contextUser = null;
        run();
    }

    //TODO write method
    private static int budgetingMenu() {
        String[] menu = {"Select Budget", "Create Budget", "Savings"};
        return ConsoleIO.promptForMenuSelection(" ", menu, true);
    }

    //           //
    // BUDGETING //
    //           //

    //TODO write method
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
                userSwitch(1);
                break;
            case 3:
                savingsMenu();
                break;
            case 0:
                input = userMenu();
                userSwitch(input);
        }
    }

    private static void chooseBudget() {
        //Creates an array containing the names of all the user's budgets, for later menu display
        String[] menu = new String[contextUser.getBudgetList().size()];
        for (int i = 0; i < menu.length; i++) {
            menu[i] = contextUser.getBudgetList().get(i).getName();
        }

        int input = ConsoleIO.promptForMenuSelection("Please select the budget you'd like to view:", menu,false);
        budgetOptionsSwitch(budgetOptionsMenu(), contextUser.getBudgetList().get(input - 1));

    }

    //TODO write method
    private static void createBudget(double funds, String name) {

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


    //TODO write method
    private static int budgetOptionsMenu() {
        String[] menu = {"Modify Budget","View History","Rename Budget","Delete Budget"};
        return ConsoleIO.promptForMenuSelection("", menu,true);
    }

    //TODO write method
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

    //TODO write method
    private static int accountSettingsMenu() {
        String[] menu = {"Change Display Name","Change Password","Change Change Security Question and Answer"};
        int choice = ConsoleIO.promptForMenuSelection("",menu,true);
        return choice;
    }

    //         //
    // ACCOUNT //
    //         //

    //TODO wrtie method
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
                String question = ConsoleIO.promptForString("Enter your security question: ",false);
                String answer = ConsoleIO.promptForString("Enter the to your security answer: ",false);
                changeSecQnA(question,answer);
                break;
            default:
                // brings the user back to the previous menu
                int input = userMenu();
                userSwitch(input);
                break;
        }
    }

    //TODO write method
    private static void changeDisplayName(String newName) {

    }

    //TODO write method
    private static void changePassword(String newPassword) {

    }

    //TODO write method
    private static void changeSecQnA(String question, String answer) {

    }

    private static void load() {
        try {
            iodataModel = new FileController().readDirectory(fileConfigurations);
            run();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

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
