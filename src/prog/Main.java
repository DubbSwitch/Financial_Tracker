package prog;

import controllers.MCBudgetController;

public class Main {

    public static void main(String[] args) { run(); }

    public static void run() {
        try {
            MCBudgetController.run();
        }catch (NullPointerException nullPointerException) {
            System.out.println("^.^");
        }
    }
}
