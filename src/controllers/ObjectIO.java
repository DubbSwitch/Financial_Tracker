package controllers;

import models.FileConfigurations;
import models.IODataModel;

import java.io.*;

public class ObjectIO {
    ObjectOutputStream getOutputStream(FileOutputStream fileOutputStream) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        return objectOutputStream;
    }

    ObjectInputStream getInputStream(FileInputStream fileInputStream) throws IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        return objectInputStream;
    }
}
