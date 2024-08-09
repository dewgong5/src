package persistence;

import model.DifficultWordsList;
import org.json.JSONObject;


import java.io.*;

//CITATION: methods are largely inspired by methods in JsonWriter's class from CPSC 210's JsonSerializationDemo
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String directory;

    // EFFECTS: constructs writer to write to the destination directory file
    public JsonWriter(String directory) {
        this.directory = directory;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if file in given directory cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(directory));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of DifficultWordsList to file
    public void write(DifficultWordsList dwlist) {
        JSONObject json = dwlist.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
