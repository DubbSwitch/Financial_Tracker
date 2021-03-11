package views;

import lib.ConsoleIO;

public class ConsoleIO2 extends ConsoleIO {
    public static double promptForDouble(String prompt, double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("The min cannot exceed the max. min=" + min + "; max=" + max);
        } else {
            double userNum = -1;
            boolean numIsInvalid = true;

            do {
                String input = promptForString(prompt, false);

                try {
                    userNum = Double.parseDouble(input);
                    numIsInvalid = userNum < min || userNum > max;
                } catch (NumberFormatException var7) {
                }

                if (numIsInvalid) {
                    System.out.println("You must enter a number between " + min + " and " + max + ". Please, try again.");
                }
            } while (numIsInvalid);

            return userNum;
        }
    }
}
