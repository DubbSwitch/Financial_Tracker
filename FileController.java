package controllers;

import lib.ConsoleIO;
import models.FileConfigurations;
import models.IODataModel;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileController {
    FileConfigurations fileConfig = new FileConfigurations();

    public void writeToFile(IODataModel data, FileConfigurations fileConfig) throws IOException {
        new ObjectIO().getOutputStream(new FileOutputStream(fileConfig.getPath() + "\\" + generateFilenameDate())).writeObject(data);
        System.out.println(fileConfig.getPath() + generateFilenameDate() + ".bdjc");
    }

    public Object readFromFile(File file) throws IOException, ClassNotFoundException {
        return new ObjectIO().getInputStream(new FileInputStream(file.getAbsolutePath())).readObject();
    }

    public IODataModel readDirectory(FileConfigurations fileConfig) throws IOException, ClassNotFoundException {
        File Dir = new File(new ConsoleIO().promptForString("Enter the path you would like to read saved data from: ", false));
        FilenameFilter filter = (f, name) -> name.endsWith(".bdjc");
        String[] pathsToFiles = Dir.list(filter);
        if (pathsToFiles != null) {
            int sel = new ConsoleIO().promptForMenuSelection(pathsToFiles, false) - 1;
            return (IODataModel) readFromFile(new File(Dir + "\\" + pathsToFiles[sel]));
        } else
            System.out.println("No .bdjc files in target directory. Does it exist?");
        return null;
    }

    private String generateFilenameDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd~MM~yyyy_HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now) + ".bdjc";
    }
}
