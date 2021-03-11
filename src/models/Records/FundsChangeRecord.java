package models.Records;

import views.ConsoleIO2;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//TODO: IF YOU MODIFY THIS CLASS IN ANY WAY, YOU MUST UPDATE serialVersionUID TO A VALID SERIAL VERSION ACROSS ALL SERIALIZABLE CLASSES!!

public class FundsChangeRecord implements Serializable  {
    private static final long serialVersionUID = 4L;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    private final LocalDateTime now = LocalDateTime.now();
    private final String timestamp = dtf.format(now);
    private final String recordID = String.valueOf(timestamp.hashCode());
    private final Double previousAvailableFundsAmount;
    private final Double newAvailableFundsAmount;
    private final String type;

    public FundsChangeRecord(Double previousAvailableFundsAmount, Double newAvailableFundsAmount, String type) {
        this.previousAvailableFundsAmount = previousAvailableFundsAmount;
        this.newAvailableFundsAmount = newAvailableFundsAmount;
        this.type = type;
    }

    public Double getNewAvailableFundsAmount() {
        return newAvailableFundsAmount;
    }

    public Double getPreviousAvailableFundsAmount() {
        return previousAvailableFundsAmount;
    }

    public String getRecordID() {
        return recordID;
    }

    public String getType() {
        return type;
    }

    /*@Override
    public String toString() {
        return "Funds Change Record" +
                "\n Record ID                       : " + recordID +
                "\n Timestamp                       : " + timestamp +
                "\n Type                            : " + type +
                "\n Previous Available Funds Amount : " + previousAvailableFundsAmount +
                "\n New Available Funds Amount      : " + newAvailableFundsAmount;
    }*/

    @Override
    public String toString() {
        double difference = newAvailableFundsAmount - previousAvailableFundsAmount;

        String symbol = "";
        if (difference > 0) {
            symbol = "+";
        } else if (difference < 0) {
            symbol = "-";
        }

        String differenceDisplay = symbol + "$" + ConsoleIO2.formatMoneyForDisplay(Math.abs(difference));
        String filler = "";
        for(int i = 0; i < 16 - differenceDisplay.length(); i++) {
            filler += " ";
        }

        String newAmountDisplay = "$" + ConsoleIO2.formatMoneyForDisplay(newAvailableFundsAmount);
        String filler2 = "";
        for(int i = 0; i < 15 - newAmountDisplay.length(); i++) {
            filler2 += " ";
        }

        return "║  " + timestamp + "  ║  " + filler + differenceDisplay + "  ║  " + filler2 + newAmountDisplay + "  ║";
    }
}
