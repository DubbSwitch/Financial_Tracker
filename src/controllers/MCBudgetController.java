package controllers;

import models.Budget;
import models.User;
import lib.ConsoleIO;

public class MCBudgetController {
    static User user; // This variable is being used to test methods while data persistence is still being setup
    public static void run() {
        int choice = homeMenu();
        homeSwitch(choice);
    }

    private static int homeMenu() {
        String[] options = {"Login", "Create New User", "Reset Password"};
        return ConsoleIO.promptForMenuSelection(options, true);
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
            case 0:
                System.out.println("Goodbye");
                break;
            default:
                System.out.println("I don't know how you did that. Your input =" + choice);
        }
    }

    //TODO write method
    private static void login() {
        String username = ConsoleIO.promptForString("Enter your Username : ", false);
        // added a try catch because if their was no users created it would cause a NulLPointerError
        try{
        if(username.equals(user.getUserName())){
            String passPrompt = "***NOTE: if you do not have an account or cannot remember your password press enter to go back***\nEnter your password:";
            boolean success = ConsoleIO.promptForBoolean(passPrompt, user.getPassword(), "");
            //IF user's input is equal to username's password continue into the account
            if (success) {
                int choice = userMenu();
                userSwitch(choice);
                //ELSE go back to the home screen
            }else {
                run();
            }
        }}catch (NullPointerException nfe){
            System.out.println("User " + username + " does not exist.  Please create an account");
            run();
        }
    }

    private static void createUser() {
        String pED = "Please enter desired";
        String userName = ConsoleIO.promptForString(pED + " username: ", false);
        String password = ConsoleIO.promptForString(pED + " password: ", true);
        String question = ConsoleIO.promptForString("Security Question for password reset\n" + pED + "Question: ", false);
        String answer = ConsoleIO.promptForString(pED + " answer: ", false);
        String displayName = ConsoleIO.promptForString(pED + " display name: ", false);
        // user is a place holder strictly for testing
        // the end of this method should create a new file with the user's information.
        user  = new User(userName, displayName, password, question, answer);
        run();
    }

    //TODO write method
    private static void resetPassword() {
        // prompts the user for their username
        //checks if that user exists
        //IF user exists
            // Prompt their security question
                // IF user input is true
                    // Prompt user for new password and set it as the users new password
                //else go back to homeMenu
    }

    //      //
    // USER //
    //      //

    //TODO write method
    private static int userMenu() {
        String[] menu = {"Budgeting","Account Settings","Logout"};
        return ConsoleIO.promptForMenuSelection(menu,false);
    }

    //TODO write method
    private static void userSwitch(int choice) {
        int input;
        switch (choice){
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
        //This should refresh the app and start from run();
    }

    //           //
    // BUDGETING //
    //           //

    //TODO write method
    private static int budgetingMenu() {
        String[] menu = {"Select Budget","Create Budget","Savings"};
        return ConsoleIO.promptForMenuSelection(menu,true);
    }

    //TODO write method
    private static void budgetingSwitch(int choice) {
        int input;
        switch (choice){
            case 1:
              input = budgetSelectionMenu();
              budgetSelectionSwitch(input);
              break;
            case 2:
                String name = ConsoleIO.promptForString("Enter name of budget: ", false);
                int funds = ConsoleIO.promptForInt("Amount of money usable: ",1,200000000);
                createBudget(funds,name);
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

    //TODO write method
    private static void createBudget(double funds, String name) {

    }

    //         //
    // SAVINGS //
    //         //

    //TODO write method
    private static void savingsMenu() {


    }

    //TODO write method
    private static void modifySavingsAmount(double amount, int choice) {

    }

    //       //
    // MENUS //
    //       //

    //TODO write method
    private static int budgetSelectionMenu() {

        return 0;
    }

    //TODO write method
    private static void budgetSelectionSwitch(int choice) {

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

    //         //
    // ACCOUNT //
    //         //

    //TODO write method
    private static int accountSettingsMenu() {

        return 0;
    }

    //TODO wrtie method
    private static void accountSettingsSwitch(int choice) {

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
}
