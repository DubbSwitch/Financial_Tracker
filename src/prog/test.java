package prog;

import controllers.FileController;
import models.FileConfigurations;
import models.IODataModel;
import java.io.IOException;
import java.nio.file.Paths;

public class test {

    //TODO *** THIS IS A TEST CLASS ***

    public static void main(String[] args) {
        FileConfigurations fileConfigurations = new FileConfigurations();
        fileConfigurations.setPath(Paths.get("<M>"));
        FileController fileController = new FileController();
        try {
            fileController.writeToFile(new IODataModel(), fileConfigurations);
            IODataModel iodataModel = fileController.readDirectory(fileConfigurations);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
