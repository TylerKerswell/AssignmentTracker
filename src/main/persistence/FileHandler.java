package persistence;

import org.json.*;
import java.io.*;

// class to read/write json objects from a given file name.
public class FileHandler {
    File file;
    private final boolean exists;


    // REQUIRES: a valid file name
    // MODIFIES: this
    // EFFECTS: opens or creates a file with the given name in the data file of this project
    public FileHandler(String fileName) throws IOException {
        file = new File("./data/" + fileName);
        try {
            exists = !file.createNewFile();
        } catch (Exception e) {
            throw new IOException();
        }

        if (!file.canWrite() || !file.canRead()) {
            throw new IOException();
        }
    }

    // EFFECTS: stores the given JSONObject into the file, returns true if successful
    public boolean writeJson(JSONObject data) throws IOException {
        FileWriter outputFile;
        try {
            outputFile = new FileWriter(file);
            outputFile.write(data.toString());
        } catch (Exception e) {
            throw new IOException();
        }
        outputFile.close();
        return true;
    }

    // EFFECTS: reads a JSON string from a given file and returns a JSONObject
    public JSONObject readJson() throws IOException {

        FileReader inputFile;

        StringBuilder inputString = new StringBuilder();

        // open file and grab a string of all its contents.
        try {
            inputFile = new FileReader(file);
            int c;
            while ((c = inputFile.read()) != -1) {
                inputString.append((char) c);
            }
        } catch (Exception e) {
            throw new IOException();
        }

        inputFile.close();

        String finalString = inputString.toString();

        return new JSONObject(finalString);
    }

    // EFFECTS: returns whether the file existed before creating the object
    public boolean fileExists() {
        return exists;
    }

}
