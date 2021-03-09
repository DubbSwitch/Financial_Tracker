package models;

import java.io.Serializable;
import java.util.ArrayList;

//TODO: IF YOU MODIFY THIS CLASS IN ANY WAY, YOU MUST UPDATE serialVersionUID TO A VALID SERIAL VERSION ACROSS ALL SERIALIZABLE CLASSES!!

public class IODataModel implements Serializable {
    private static final long serialVersionUID = 5L;
    private ArrayList<Budget> validBudget = new ArrayList<>();
    private ArrayList<User> validUsers = new ArrayList<>();

    public void addUser(User user) {
        validUsers.add(user);
    }

    public void addBudget(Budget budget) {
        validBudget.add(budget);
    }

    public ArrayList<Budget> getBudget() {
        return validBudget;
    }

    public ArrayList<User> getUsers() {
        return validUsers;
    }

    @Override
    public String toString() {
        return "IODataModel{" +
                "validBudget=" + validBudget +
                ", validUsers=" + validUsers +
                '}';
    }
}
