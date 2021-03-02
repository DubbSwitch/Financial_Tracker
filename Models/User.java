package Models;

import lib.ConsoleIO;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {
    private String userName;
    private String displayName;
    private String password;
    private String secQuestion;
    private String secAnswer;
    private ArrayList<Budget> budgetList;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecQuestion() {
        return secQuestion;
    }

    public void setSecQuestion(String secQuestion) {
        this.secQuestion = secQuestion;
    }

    public String getSecAnswer() {
        return secAnswer;
    }

    public void setSecAnswer(String secAnswer) {
        this.secAnswer = secAnswer;
    }

    public void addNewBudget(double amount, Budget budget){

    }
    public void deleteBudget(int choice) {
        if(choice == 0){
            System.out.println("There are no budgets to delete");
        }
        else
        budgetList.remove(choice-1);
    }
    public void deposit(double amount, Budget budget){}
    public void withdraw(double amount, Budget budget){}

    public User(String userName, String displayName, String password, String secQuestion, String secAnswer) {
        setUserName(userName);
        setDisplayName(displayName);
        setPassword(password);
        setSecQuestion(secQuestion);
        setSecAnswer(secAnswer);
    }
}
