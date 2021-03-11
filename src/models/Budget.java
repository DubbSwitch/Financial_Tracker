package models;

import models.Records.BudgetChangeRecord;
import models.Records.FundsChangeRecord;

import java.io.Serializable;
import java.util.ArrayList;

//TODO: IF YOU MODIFY THIS CLASS IN ANY WAY, YOU MUST UPDATE serialVersionUID TO A VALID SERIAL VERSION ACROSS ALL SERIALIZABLE CLASSES!!

public class Budget implements Serializable {
    private static final long serialVersionUID = 4L;
    private ArrayList<BudgetChangeRecord> budgetOverTime = new ArrayList<>();
    private final ArrayList<FundsChangeRecord> fundsHistory = new ArrayList<>();
    private double funds;
    private double budgetAmount;
    private String name;

    public Budget(double budgetAmount,double funds, String name) {
        setFunds(funds);
        setName(name);
        setBudgetAmount(budgetAmount);
    }

    //TODO Maybe prevent users from going over budget????

    public double deposit(double amount) {
        fundsHistory.add(new FundsChangeRecord(funds, (funds + amount), "DEPOSIT"));
        funds += amount;
        return funds;
    }

    //TODO write method
    public double withdraw(double amount) {
        if(funds - amount >= budgetAmount){
            System.out.println("Unable to go over limit of " + amount + ". Transaction not tracked.");
        }
        else{
        fundsHistory.add(new FundsChangeRecord(funds, (funds - amount), "WITHDRAW"));
        funds -= amount;}
        return funds;
    }

    private void changeBudget(double newBudgetAmount) {
        budgetOverTime.add(new BudgetChangeRecord(budgetAmount, newBudgetAmount));
        budgetAmount = newBudgetAmount;
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

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    @Override
    public String toString() {
        String summary ="Budget: " + getName() + "\nTotal Amount: " + getBudgetAmount()
                + "Available Funds: "+ getFunds();
        return summary;
    }
}
