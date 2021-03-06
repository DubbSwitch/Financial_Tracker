package controllers;

import models.User;
import lib.ConsoleIO;

public class MCBudgetController {
    public static void run(){
    int choice = homeMenu();
    homeSwitch(choice);
    }
    private static int homeMenu(){
        String[] options = {"Login","Create New User","Reset Password"};
        return ConsoleIO.promptForMenuSelection(options,true);
    }
    private static void homeSwitch(int choice){
        switch (choice){
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
    private static void login(){

    }
    private static void createUser(){
        String pED = "Please enter desired";
    String userName = ConsoleIO.promptForString(pED+ " username: ",false);
    String password = ConsoleIO.promptForString(pED + " password",true);
    String question = ConsoleIO.promptForString("Security Question for password reset\n" + pED+ "Question",false);
    String answer = ConsoleIO.promptForString(pED + "answer: ", false);
    String displayName = ConsoleIO.promptForString(pED + "display name: ",false);
       new User(userName,displayName,password,question,answer);
        run();
    }
    private static void resetPassword(){

    }
}
