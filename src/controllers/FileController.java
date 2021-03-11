package controllers;

import lib.ConsoleIO;
import models.FileConfigurations;
import models.IODataModel;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileController {
    String saveDataFileName = "MCBudget_SaveData";

    public void writeToFile(IODataModel data) throws IOException {
        FileOutputStream fos = new FileOutputStream(saveDataFileName + ".file");
        new ObjectIO().getOutputStream(fos).writeObject(data);
        fos.close();
    }

    public IODataModel readDirectory() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(saveDataFileName + ".file");
        Object obj = new ObjectIO().getInputStream(fis).readObject();
        fis.close();
        return (IODataModel) obj;
    }
}
