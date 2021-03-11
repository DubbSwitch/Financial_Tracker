package models;

import models.Records.FundsChangeRecord;

import java.io.Serializable;
import java.util.ArrayList;

//TODO: IF YOU MODIFY THIS CLASS IN ANY WAY, YOU MUST UPDATE serialVersionUID TO A VALID SERIAL VERSION ACROSS ALL SERIALIZABLE CLASSES!!

public class IODataModel implements Serializable {
    private static final long serialVersionUID = 5L;
    private final ArrayList<Budget> validBudget = new ArrayList<>();
    private final ArrayList<User> validUsers = new ArrayList<>();
    private final ArrayList<FundsChangeRecord> validFundsChangeRecords = new ArrayList<>();

    public void addUser(User user) {
        validUsers.add(user);
    }

    public void addBudget(Budget budget) {
        validBudget.add(budget);
    }

    public void addFundsChangeRecord(FundsChangeRecord fcr) { validFundsChangeRecords.add(fcr); }

    public ArrayList<Budget> getBudget() {
        return validBudget;
    }

    public ArrayList<User> getUsers() {
        return validUsers;
    }

    public ArrayList<FundsChangeRecord> getValidFundsChangeRecords() {
        return validFundsChangeRecords;
    }

    @Override
    public String toString() {
        return "IODataModel{" +
                "validBudget=" + validBudget +
                ", validBudget=" + validBudget +
                ", validFundsChangeRecords=" + validFundsChangeRecords +
                '}';
    }
}
