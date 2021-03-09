package models;

import models.Records.BudgetChangeRecord;
import models.Records.FundsChangeRecord;

import java.io.Serializable;
import java.util.ArrayList;

//TODO: IF YOU MODIFY THIS CLASS IN ANY WAY, YOU MUST UPDATE serialVersionUID TO A VALID SERIAL VERSION ACROSS ALL SERIALIZABLE CLASSES!!

public class Budget implements Serializable {
    private static final long serialVersionUID = 5L;
    private ArrayList<BudgetChangeRecord> budgetOverTime = new ArrayList<>();
    private ArrayList<FundsChangeRecord> fundsHistory = new ArrayList<>();
    private double funds;
    private double budgetAmmout;
    private String name;

    public Budget(double funds, String name) {
        setFunds(funds);
        setName(name);
    }

    //TODO Maybe prevent users from going over budget?

    public double deposit(double amount) {
        fundsHistory.add(new FundsChangeRecord(funds, (funds + amount), "DEPOSIT"));
        funds += amount;
        return funds;
    }

    //TODO write method
    public double withdraw(double amount) {
        fundsHistory.add(new FundsChangeRecord(funds, (funds - amount), "WITHDRAW"));
        funds -= amount;
        return funds;
    }

    private void changeBudget(double newBudgetAmmout) {
        budgetOverTime.add(new BudgetChangeRecord(budgetAmmout, newBudgetAmmout));
        budgetAmmout = newBudgetAmmout;
    }

    public ArrayList<BudgetChangeRecord> getBudgetOverTime() {
        return budgetOverTime;
    }

    public void setBudgetOverTime(ArrayList<BudgetChangeRecord> budgetOverTime) {
        this.budgetOverTime = budgetOverTime;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
