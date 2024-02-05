package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static tests.GlobalVariables.currentWorkingDirectory;

public class CSVHandler {

    public static final String delimiter = ",";

    //reads any given csv file and saves it into a specified list of strings
    public static void readCSVFileAndSaveContentAsListOfStrings(String csvFile, List<String> csvData) {
        try {
            File file = new File(csvFile);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String[] csvArray;
            String line = " ";
            while ((line = bufferedReader.readLine()) != null) {
                csvArray = line.split(delimiter);
                csvData.addAll(Arrays.asList(csvArray));
            }
            bufferedReader.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void saveListDataToTxtFile(List<String> listToUse) throws IOException {
//        FileWriter writer = new FileWriter(currentWorkingDirectory  + System.currentTimeMillis() + "_results" + ".txt");
        FileWriter writer = new FileWriter(new File(currentWorkingDirectory + "/siteCrawlerResults/", System.currentTimeMillis() + "_results" + ".txt"));

        String collect = String.join(",", listToUse);
        System.out.println(collect);

        writer.write(collect);
        writer.close();
    }

}
