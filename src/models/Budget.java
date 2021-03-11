package models;

import controllers.MCBudgetController;
import models.Records.FundsChangeRecord;
import views.ConsoleIO2;

import java.io.Serializable;
import java.util.ArrayList;

//TODO: IF YOU MODIFY THIS CLASS IN ANY WAY, YOU MUST UPDATE serialVersionUID TO A VALID SERIAL VERSION ACROSS ALL SERIALIZABLE CLASSES!!

public class Budget implements Serializable {
    private static final long serialVersionUID = 4L;
    private final ArrayList<FundsChangeRecord> fundsHistory = new ArrayList<>();
    private double funds;
    private double budgetAmount;
    private String name;

    public Budget(double budgetAmount,double funds, String name) {
        setFunds(funds);
        setName(name);
        setBudgetAmount(budgetAmount);
        fundsHistory.add(new FundsChangeRecord(funds, funds));
    }

    //TODO Maybe prevent users from going over budget????

    public double deposit(double amount) {
        fundsHistory.add(new FundsChangeRecord(funds, (funds + amount)));
        funds += amount;
        MCBudgetController.save();
        return funds;
    }

    //TODO write method
    public double withdraw(double amount) {
        fundsHistory.add(new FundsChangeRecord(funds, (funds - amount)));
        funds -= amount;
        MCBudgetController.save();
        return funds;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
        MCBudgetController.save();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        MCBudgetController.save();
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
        MCBudgetController.save();
    }

    public ArrayList<FundsChangeRecord> getFundsChangeRecord() {
        return fundsHistory;
    }

    @Override
    public String toString() {
        return getName() + ": $" + ConsoleIO2.formatMoneyForDisplay(getFunds()) + " spent of $" + ConsoleIO2.formatMoneyForDisplay(getBudgetAmount()) + " spending cap.";
    }
}
