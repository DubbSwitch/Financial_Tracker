package models;

import controllers.MCBudgetController;

import java.io.Serializable;
import java.util.ArrayList;

//TODO: IF YOU MODIFY THIS CLASS IN ANY WAY, YOU MUST UPDATE serialVersionUID TO A VALID SERIAL VERSION ACROSS ALL SERIALIZABLE CLASSES!!

public class User implements Serializable {
    private static final long serialVersionUID = 4L;
    private String userName;
    private String displayName;
    private String password;
    private String secQuestion;
    private String secAnswer;
    private ArrayList<Budget> budgetList = new ArrayList<>();

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

    public void addNewBudget(Budget budget) {
        try {
            budgetList.add(budget);
        }catch (NullPointerException npe){
            System.out.println("NullPointerException has been caught, this method is doing something wrong.");
        }
        MCBudgetController.save();
    }

    public ArrayList<Budget> getBudgetList() { return budgetList; }

    public User(String userName, String displayName, String password, String secQuestion, String secAnswer) {
        setUserName(userName);
        setDisplayName(displayName);
        setPassword(password);
        setSecQuestion(secQuestion);
        setSecAnswer(secAnswer);
    }
}
