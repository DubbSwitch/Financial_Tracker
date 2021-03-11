package controllers;

import models.FileConfigurations;
import models.IODataModel;

import java.io.*;

public class ObjectIO {
    ObjectOutputStream getOutputStream(FileOutputStream fileOutputStream) throws IOException {
        return new ObjectOutputStream(fileOutputStream);
    }

    ObjectInputStream getInputStream(FileInputStream fileInputStream) throws IOException {
        return new ObjectInputStream(fileInputStream);
    }
}
