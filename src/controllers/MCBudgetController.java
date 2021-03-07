package controllers;

import models.Budget;
import models.User;
import lib.ConsoleIO;

public class MCBudgetController {

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

    }

    private static void createUser() {
        String pED = "Please enter desired";
        String userName = ConsoleIO.promptForString(pED + " username: ", false);
        String password = ConsoleIO.promptForString(pED + " password", true);
        String question = ConsoleIO.promptForString("Security Question for password reset\n" + pED + "Question", false);
        String answer = ConsoleIO.promptForString(pED + "answer: ", false);
        String displayName = ConsoleIO.promptForString(pED + "display name: ", false);
        new User(userName, displayName, password, question, answer);
        run();
    }

    //TODO write method
    private static void resetPassword() {

    }

    //      //
    // USER //
    //      //

    //TODO write method
    private int userMenu() {

        return 0;
    }

    //TODO write method
    private void userSwitch(int choice) {

    }

    //TODO write method
    private void logout() {

    }

    //           //
    // BUDGETING //
    //           //

    //TODO write method
    private int budgetingMenu() {

        return 0;
    }

    //TODO write method
    private void budgetingSwitch(int choice) {

    }

    //TODO write method
    private void createBudget(double funds, String name) {

    }

    //         //
    // SAVINGS //
    //         //

    //TODO write method
    private int savingsMenu() {

        return 0;
    }

    //TODO write method
    private void modifySavingsAmount(double amount, int choice) {

    }

    //       //
    // MENUS //
    //       //

    //TODO write method
    private int budgetSelectionmenu() {

        return 0;
    }

    //TODO write method
    private void budgetSelectionSwitch(int choice) {

    }

    //TODO write method
    private void modifyBudgetMenu() {

    }

    //TODO write method
    private void viewTransactionHistory(Budget budget) {

    }

    //TODO write method
    private void deleteBudget(Budget budget) {

    }

    //TODO write method
    private void renameBudget(Budget budget, String newName) {

    }

    //         //
    // ACCOUNT //
    //         //

    //TODO write method
    private int accountSettingsMenu() {

        return 0;
    }

    //TODO wrtie method
    private void accountSeetingsSwitch(int choice) {

    }

    //TODO write method
    private void changeDisplayName(String newName) {

    }

    //TODO write method
    private void changePassword(String newPassword) {

    }

    //TODO write method
    private void changeSecQnA(String question, String answer) {

    }
}
