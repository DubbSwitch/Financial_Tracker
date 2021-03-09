package models.Records;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//TODO: IF YOU MODIFY THIS CLASS IN ANY WAY, YOU MUST UPDATE serialVersionUID TO A VALID SERIAL VERSION ACROSS ALL SERIALIZABLE CLASSES!!

public class FundsChangeRecord implements Serializable  {
    private static final long serialVersionUID = 4L;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH-mm-ss-ms");
    private final LocalDateTime now = LocalDateTime.now();
    private final String timestamp = dtf.format(now);
    private final String recordID = String.valueOf(timestamp.hashCode());
    private Double previousAvailableFundsAmount;
    private Double newAvailableFundsAmount;
    private String type;

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

    @Override
    public String toString() {
        return "Funds Change Record" +
                "\n Record ID                       : " + recordID +
                "\n Timestamp                       : " + timestamp +
                "\n Type                            : " + type +
                "\n Previous Available Funds Amount : " + previousAvailableFundsAmount +
                "\n New Available Funds Amount      : " + newAvailableFundsAmount;
    }
}
