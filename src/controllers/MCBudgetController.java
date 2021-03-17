package controllers;

import lib.ConsoleIO;
import models.Budget;
import models.IODataModel;
import models.User;
import views.ConsoleIO2;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class MCBudgetController {
    static IODataModel iodataModel = new IODataModel(); //Use for saving data, and managing users.
    static UserContextController UserContextController = new UserContextController();
    static User contextUser;
    static Budget contextBudget;
    static boolean closeProgram;

    public static void run() {
        load();
        homeSwitch(homeMenu());
    }

    //Methods ending in menu return an integer after prompting the player for a menu selection.
    private static int homeMenu() {
        String[] options = {"Login", "Create New User", "Reset Password"};
        return ConsoleIO.promptForMenuSelection(" ", options, true);
    }

    //Methods ending in Switch take in a menu selection and choose a corresponding action.
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
                System.out.println("Goodbye.");
                closeProgram = true;
                return;
            default:
                System.out.println("I don't know how you did that. Your input =" + choice);
        }
        if (!closeProgram) {
            homeSwitch(homeMenu());
        }
    }

    //Finished
    private static void login() {
        String username = ConsoleIO.promptForString("Enter your Username: ", false);
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
    }

    private static void createUser() {
        String pED = "Please enter desired";
        String username = ConsoleIO.promptForString(pED + " username: ", false);
        while (usernameIsTaken(username)) {
            username = ConsoleIO.promptForString("That username is unavailable. Please enter a different username.",false);
        }
        String password = ConsoleIO.promptForString(pED + " password: ", true);
        String question = ConsoleIO.promptForString("Please enter a security question for your account: ", false);
        String answer = ConsoleIO.promptForString("Please enter an answer for your security question: ", false);
        String displayName = ConsoleIO.promptForString(pED + " display name: ", false);

        System.out.println("User " + displayName + " successfully created. Please log in.");
        contextUser = new User(username, displayName, password, question, answer);
        iodataModel.addUser(contextUser);
        save();
    }

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
        }

        save();
    }


    private static int userMenu() {
        String[] menu = {"Budgeting", "Account Settings", "Logout"};
        return ConsoleIO.promptForMenuSelection("Welcome "  + contextUser.getDisplayName() + ",",menu, false);
    }

    //      //
    // USER //
    //      //

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

    private static void logout() {
        contextUser = null;
        save();
    }

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
                double funds = ConsoleIO2.promptForDouble("Please enter how much you've already spent towards this budget: ", 0, maxAmount);

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
            budgetOptionsSwitch(budgetOptionsMenu(), contextBudget);
        } catch (NullPointerException | IllegalArgumentException error){
            System.out.println("There are no budgets associated with your account");
            int path = budgetingMenu();
            budgetingSwitch(path);
        }
    }

    //finished
    private static void createBudget(double maxAmount,double funds, String name) {
        Budget newB = new Budget(maxAmount,funds,name);
        contextUser.addNewBudget(newB);
        iodataModel.addBudget(newB);
        contextBudget = newB;
        budgetOptionsSwitch(budgetOptionsMenu(), contextBudget);
    }

    //       //
    // MENUS //
    //       //

    private static int budgetOptionsMenu() {
        String[] menu = {"New Transaction","View History","Change Budget Cap","Rename Budget","Delete Budget"};
        return ConsoleIO.promptForMenuSelection(contextBudget.toString(), menu,true);
    }

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
                    deleteBudget();
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
    private static void modifyBudgetFundsMenu() {
        String[] menu = {"Add To Budget","Subtract From Budget"};
        int choice =  ConsoleIO.promptForMenuSelection("",menu,true);
        switch (choice){
            case 1:
                double in = round(ConsoleIO2.promptForDouble("Enter how much you would like to add/deposit to your budget total: ",-Double.MAX_VALUE,Double.MAX_VALUE),2);
                if (in > 0) {
                    //Prevent user from increasing spent amount beyond the max spending cap.
                    if (in + contextBudget.getFunds() <= contextBudget.getBudgetAmount()) {
                        contextBudget.deposit(round(in,2));
                    } else {
                        double newMaxCap = in + contextBudget.getFunds();
                        String prompt = "Adding $" + in + " to your budget would bring it above your spending cap." +
                                "\nTo increase your budget funds by $" + in + ", you must increase your spending cap to $" + newMaxCap + ".";
                        int choice2 = ConsoleIO.promptForMenuSelection(prompt, new String[]{"Yes, increase budget funds and spending cap to $" + newMaxCap + "."}, true);
                        switch (choice2){
                            case 1:
                                contextBudget.setBudgetAmount(newMaxCap);
                                contextBudget.deposit(round(in,2));
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
                double out = round(ConsoleIO2.promptForDouble("Enter how much you would like to deduct/spend from your budget total:  ",-Double.MAX_VALUE,Double.MAX_VALUE),2);
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
        save();
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

    private static void viewTransactionHistory(Budget budget) {
        if (budget.getFundsChangeRecord().size() > 0) {
            System.out.println("╔═══════════════════════╦════════════════════╦═══════════════════╗");
            System.out.println("║     DATE AND TIME     ║       CHANGE       ║    NEW BALANCE    ║");
            System.out.println("╠═══════════════════════╬════════════════════╬═══════════════════╣");
            for (int i = 0; i < budget.getFundsChangeRecord().size(); i++) {
                System.out.println(budget.getFundsChangeRecord().get(i).toString());
                if (!(i + 1 == budget.getFundsChangeRecord().size())) {
                    System.out.println("╠═══════════════════════╬════════════════════╬═══════════════════╣");
                } else {
                    System.out.println("╠═══════════════════════╩════════════════════╩═══════════════════╣");
                }
            }
            String filler = "";
            String currentBalanceDisplay = ConsoleIO2.formatMoneyForDisplay(budget.getFunds());
            for(int i = 0; i < 42 - currentBalanceDisplay.length(); i++) {
                filler += " ";
            }
            System.out.println("║  " + filler + "Current Balance: $" + currentBalanceDisplay + "  ║");
            System.out.println("╚════════════════════════════════════════════════════════════════╝\n\n");
        } else {
            System.out.println("\nNo transaction history exists.\n");
        }
        budgetOptionsSwitch(budgetOptionsMenu(),budget);
    }

    private static void deleteBudget() {
        for (int i = 0; i < contextUser.getBudgetList().size(); i++) {
            if (contextUser.getBudgetList().get(i) == contextBudget) {
                contextBudget = null;
                contextUser.getBudgetList().remove(i);

            }
        }
        budgetingSwitch(budgetingMenu());
        save();
    }

    private static void renameBudget(Budget budget, String newName) {
        budget.setName(newName);
        int input = budgetOptionsMenu();
        budgetOptionsSwitch(input,budget);
        save();
    }


    private static int accountSettingsMenu() {
        String[] menu = {"Change Display Name","Change Password","Change Security Question and Answer"};
        return ConsoleIO.promptForMenuSelection("Welcome to your account settings " + contextUser.getDisplayName(),menu,true);
    }

    //         //
    // ACCOUNT //
    //         //

    private static boolean usernameIsTaken(String username) {
        boolean taken = false;
        for (int i = 0; i < iodataModel.getUsers().size(); i++) {
            if (iodataModel.getUsers().get(i).getUserName().equalsIgnoreCase(username.toLowerCase())) {
                taken = true;
                break;
            }
        }
        return taken;
    }

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


    private static void changeDisplayName(String newName) {
        System.out.println("Display name changed to " + newName + ".");
        contextUser.setDisplayName(newName);
        save();
        userSwitch(2); // This automatically takes the user back to the account settings menu
    }

    //Finished
    private static void changePassword(String newPassword) {
        System.out.println("Password changed.");
        contextUser.setPassword(newPassword);
        save();
        userSwitch(2);
    }

    //Finished
    private static void changeSecQnA(String question, String answer) {
        System.out.println("Security questions changed.");
        contextUser.setSecQuestion(question);
        contextUser.setSecAnswer(answer);
        save();
        userSwitch(2);
    }

    //                  //
    // Data Persistence //
    //                  //

    private static void load() {
        try {
            iodataModel = new FileController().readDirectory();
        } catch (IOException | ClassNotFoundException e) {
        }
    }

    public static void save() {
        try {
            new FileController().writeToFile(iodataModel);
        } catch (NotSerializableException nse) {
            System.out.println("NSE");
            nse.printStackTrace();
        } catch (InvalidClassException ice) {
            System.out.println("ICE");
        } catch (IOException ioe) {
            System.out.println("IOE");
        }
    }

    //      //
    // Math //
    //      //

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    //                 //
    // Getters/Setters //
    //                 //


    public static IODataModel getIodataModel() {
        return iodataModel;
    }
}
