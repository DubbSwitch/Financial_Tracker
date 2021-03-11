package models.Records;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//TODO: IF YOU MODIFY THIS CLASS IN ANY WAY, YOU MUST UPDATE serialVersionUID TO A VALID SERIAL VERSION ACROSS ALL SERIALIZABLE CLASSES!!

public class BudgetChangeRecord implements Serializable {
    private static final long serialVersionUID = 4L;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH-mm-ss-ms");
    private final LocalDateTime now = LocalDateTime.now();
    private final String timestamp = dtf.format(now);
    private final String recordID = String.valueOf(timestamp.hashCode());
    private final double fromFundsAvailable;
    private final double toFundsAvailable;

    public BudgetChangeRecord(final double fromFundsAvailable, final double toFundsAvailable) {
        this.fromFundsAvailable = fromFundsAvailable;
        this.toFundsAvailable = toFundsAvailable;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getPreviousFundsAvailable() {
        return fromFundsAvailable;
    }

    public double getToNewFundsAvailable() {
        return toFundsAvailable;
    }

    public String getRecordID() {
        return recordID;
    }

    @Override
    public String toString() {
        return "BudgetChangeRecord" +
                "\n Record ID        : " + recordID +
                "\n Record timestamp : " + timestamp +
                "\n Old budget       : " + fromFundsAvailable +
                "\n New budget       : " + toFundsAvailable;
    }
}
