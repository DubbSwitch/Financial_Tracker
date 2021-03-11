package controllers;

import lib.ConsoleIO;
import models.Budget;
import models.FileConfigurations;
import models.IODataModel;
import models.Records.FundsChangeRecord;
import models.User;
import views.ConsoleIO2;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MCBudgetController {
    private static final FileConfigurations fileConfigurations = new FileConfigurations();
    static IODataModel iodataModel = new IODataModel(); //Use for saving data, and managing users.
    static UserContextController UserContextController = new UserContextController();
    static User contextUser; //current user
    static Budget contextBudget;

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
        String question = ConsoleIO.promptForString("Please enter a security question for your account: ", false);
        String answer = ConsoleIO.promptForString("Please enter an answer for your security question: ", false);
        String displayName = ConsoleIO.promptForString(pED + " display name: ", false);
        // user is a place holder strictly for testing
        // the end of this method should create a new file with the user's information.
        //TODO validate save
        System.out.println("User " + displayName + " succesfully created. Please log in.");
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

    //Finished
    private static int userMenu() {
        String[] menu = {"Budgeting", "Account Settings", "Logout"};
        return ConsoleIO.promptForMenuSelection("Welcome "  + contextUser.getDisplayName() + ",",menu, false);
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
        String[] menu = {"Select Budget", "Create Budget"};
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
                String name = ConsoleIO.promptForString("Please enter a name for your new budget: ", false);
                double maxAmount = ConsoleIO2.promptForDouble("Please enter the maximum spending cap for your new budget: ", 1, 200000000);
                double funds = ConsoleIO2.promptForDouble("Please enter how much you've already spent towards this budget: ", 1, 200000000);

                createBudget(maxAmount,funds,name);
                break;
            case 0:
                input = userMenu();
                userSwitch(input);
        }
    }
    // Finished
    private static void chooseBudget() {
        //Creates an array containing the names of all the user's budgets, for later menu display
        try {
            String[] menu = new String[contextUser.getBudgetList().size()];
            for (int i = 0; i < menu.length; i++) {
                menu[i] = contextUser.getBudgetList().get(i).getName();
            }

            int input = ConsoleIO.promptForMenuSelection("Please select the budget you'd like to view:", menu, false);
            contextBudget = contextUser.getBudgetList().get(input -1);
            //  budgetOptionsSwitch(budgetOptionsMenu(), contextUser.getBudgetList().get(input - 1));
            budgetOptionsSwitch(budgetOptionsMenu(), contextBudget);
        } catch (NullPointerException | IllegalArgumentException error){
            System.out.println("There are no budgets associated with your account");
            int path = budgetingMenu();
            budgetingSwitch(path);
        }
    }

    //finished
    private static void createBudget(double maxAmount,double funds, String name) {
        contextBudget = new Budget(maxAmount,funds,name);
        contextUser.addNewBudget(contextBudget);
        budgetOptionsSwitch(budgetOptionsMenu(), contextBudget);
    }

    /*TODO write method
    private static void savingsMenu() {

    }*/

    //         //
    // SAVINGS //
    //         //

    //TODO write method
//    private static void modifySavingsAmount(double amount, int choice) {
//
//    }

    //       //
    // MENUS //
    //       //

    //Finished
    private static int budgetOptionsMenu() {
        String[] menu = {"New Transaction","View History","Change Budget Cap","Rename Budget","Delete Budget"};
        return ConsoleIO.promptForMenuSelection(contextBudget.toString(), menu,true);
    }

    //Finished
    private static void budgetOptionsSwitch(int choice, Budget budget) {
        switch (choice) {
            case 1:
                modifyBudgetFundsMenu();
                break;
            case 2:
                viewTransactionHistory(budget);
                break;
            case 3:
                modifyBudgetCapMenu();
                break;
            case 4:
                renameBudget(budget, ConsoleIO.promptForString("Please enter a new name for the budget:",false));
                break;
            case 5:
                String response = ConsoleIO.promptForString("Warning: Deleting a budget and its full transaction history is permanent and cannot be undone.\nTo confirm deletion, please enter your password.", true);
                if (response.equals(contextUser.getPassword())) {
                    deleteBudget(budget);
                } else {
                    System.out.println("Incorrect password. Your budget has not been deleted.");
                    budgetOptionsSwitch(budgetOptionsMenu(), budget);
                }
                break;
            case 0:
                contextBudget = null;
                userSwitch(1);
                break;
        }
    }

    //finished
    private static void modifyBudgetFundsMenu() {
        String[] menu = {"New Expense","Remove Expense"};
        int choice =  ConsoleIO.promptForMenuSelection("",menu,true);
        switch (choice){
            case 1:
                double in = round(ConsoleIO2.promptForDouble("Enter how much you would like to add to your spent total: ",-Double.MAX_VALUE,Double.MAX_VALUE),2);
                if (in > 0) {
                    if (in + contextBudget.getFunds() <= contextBudget.getBudgetAmount()) {
                        contextBudget.deposit(round(in,2));
                    } else {
                        double newMaxCap = in + contextBudget.getFunds();
                        String prompt = "Adding $" + in + " to your budget would bring it above your spending cap." +
                                "\nTo increase your spent funds by $" + in + ", you must increase your spending cap to $" + newMaxCap + ".";
                        int choice2 = ConsoleIO.promptForMenuSelection(prompt, new String[]{"Yes, increase spent funds and spending cap to $" + newMaxCap + "."}, true);
                        switch (choice2){
                            case 1:
                                contextBudget.setBudgetAmount(newMaxCap);
                                contextBudget.setFunds(newMaxCap);
                                break;
                            case 0:
                                break;
                        }
                    }
                } else {
                    System.out.println("Invalid amount entered.");
                }
                break;
            case 2:
                double out = round(ConsoleIO2.promptForDouble("Enter how much you would like to deduct from your spent total:  ",-Double.MAX_VALUE,Double.MAX_VALUE),2);
                if (out > 0) {
                    if (contextBudget.getFunds() - out >= 0) {
                        contextBudget.withdraw(round(out,2));
                    } else {
                        System.out.println("Error: You tried to deduct less from your budget's spent total than it contains.");
                    }
                } else {
                    System.out.println("Invalid amount entered.");
                }
                break;
            case 0:
                break;
        }
        budgetOptionsSwitch(budgetOptionsMenu(),contextBudget);
    }

    private static void modifyBudgetCapMenu() {
        int prompt = ConsoleIO.promptForInt("Enter a new budget cap.",1,999999999);
        if (prompt >= contextBudget.getFunds()) {
            contextBudget.setBudgetAmount(prompt);
        } else {
            System.out.println("You cannot set the budget cap lower than the current funds.");
        }
        budgetOptionsSwitch(budgetOptionsMenu(),contextBudget);
    }

    //TODO write method
    private static void viewTransactionHistory(Budget budget) {
        if (contextBudget.getFundsChangeRecord().size() > 0) {
            System.out.println("╔═══════════════════════╦════════════════════╦═══════════════════╗");
            System.out.println("║     DATE AND TIME     ║       CHANGE       ║    NEW BALANCE    ║");
            System.out.println("╠═══════════════════════╬════════════════════╬═══════════════════╣");
            for (int i = 0; i < contextBudget.getFundsChangeRecord().size(); i++) {
                System.out.println(contextBudget.getFundsChangeRecord().get(i).toString());
                if (!(i + 1 == contextBudget.getFundsChangeRecord().size())) {
                    System.out.println("╠═══════════════════════╬════════════════════╬═══════════════════╣");
                } else {
                    System.out.println("╠═══════════════════════╩════════════════════╩═══════════════════╣");
                    //System.out.println("╚═══════════════════════╩════════════════════╩═══════════════════╝");
                }
            }
            String filler = "";
            String currentBalanceDisplay = ConsoleIO2.formatMoneyForDisplay(budget.getFunds());
            for(int i = 0; i < 42 - currentBalanceDisplay.length(); i++) {
                filler += " ";
            }
            System.out.println("║  " + filler + "Current Balance: $" + currentBalanceDisplay + "  ║");
            System.out.println("╚════════════════════════════════════════════════════════════════╝\n\n");
            budgetOptionsSwitch(budgetOptionsMenu(),contextBudget);
        } else {
            System.out.println("\nNo transaction history exists.\n");
            budgetOptionsSwitch(budgetOptionsMenu(),contextBudget);
        }
    }

    //TODO write method
    private static void deleteBudget(Budget budget) {
        for (int i = 0; i < contextUser.getBudgetList().size(); i++) {
            if (contextUser.getBudgetList().get(i) == contextBudget) {
                contextBudget = null;
                contextUser.getBudgetList().remove(i);
                budgetingSwitch(budgetingMenu());
            }
        }
    }

    //TODO write method
    private static void renameBudget(Budget budget, String newName) {
        budget.setName(newName);
        int input = budgetOptionsMenu();
        budgetOptionsSwitch(input,budget);
    }

    //Finished
    private static int accountSettingsMenu() {
        String[] menu = {"Change Display Name","Change Password","Change Security Question and Answer"};
        return ConsoleIO.promptForMenuSelection("Welcome to your account settings " + contextUser.getDisplayName(),menu,true);
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
                String response = ConsoleIO.promptForString("Please enter your old password: ", true);
                if (response.equals(contextUser.getPassword())) {
                    changePassword(ConsoleIO.promptForString("Please enter a new password: ",false));

                } else {
                    System.out.println("Incorrect password.");
                    accountSettingsSwitch(accountSettingsMenu());
                }
                break;
            case 3:
                String question = ConsoleIO.promptForString("Please enter a new security question: ",false);
                String answer = ConsoleIO.promptForString("Please enter the answer to your new security question: ",false);
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
        if (fileConfigurations.getPath() != null) {
            System.out.println("Use saved path: '" + fileConfigurations.getPath() + "'?");
            ConsoleIO.promptForMenuSelection(" ", new String[]{"Yes", "No"}, false);
        }
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
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
