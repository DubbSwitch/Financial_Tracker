package views;

import java.io.*;

public class FileIO {
    public static void writeTextToFile(String filePath, String text){
        try {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(text);
            myWriter.close();
        }catch (IOException ioe) {
            System.out.println("error");
            ioe.printStackTrace();
        }
    }
    public static String readTextFromFile(String filePath) {
        File something = new File(filePath);
        String text = "Couldn't find anything";
        if(something.exists()) {

            try {
                BufferedReader br = new BufferedReader(new FileReader(filePath));
                text = br.readLine();

            } catch (Exception fnfe) {
                System.out.println("Error, diary entry doesn't exist");
                fnfe.printStackTrace();
            }
        }
        return text;
    }
}
