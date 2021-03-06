package models;

import java.util.ArrayList;

public class Budget {

    private ArrayList<Double> budgetOverTime = new ArrayList<>();
    private double funds;
    private String name;

    public double deposit(double amount) {

        return 0;
    }

    public double withdraw(double amount) {

        return 0;
    }

    public ArrayList<Double> getBudgetOverTime() {
        return budgetOverTime;
    }

    public void setBudgetOverTime(ArrayList<Double> budgetOverTime) {
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

    public Budget(double funds, String name) {
        setFunds(funds);
        setName(name);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
