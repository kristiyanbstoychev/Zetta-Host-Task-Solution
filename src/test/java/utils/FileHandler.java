package utils;

import java.io.*;
import java.util.List;

import static tests.GlobalVariables.currentWorkingDirectory;

public class FileHandler {
    //saves a list of strings to a .txt file in a specified directory
    public static void saveListDataToTxtFile(List<String> listToUse, String directory) throws IOException {
        FileWriter writer = new FileWriter(new File(directory, System.currentTimeMillis() + "_results" + ".txt"));

        String collect = String.join(",", listToUse);
        System.out.println(collect);

        writer.write(collect);
        writer.close();
    }

}
